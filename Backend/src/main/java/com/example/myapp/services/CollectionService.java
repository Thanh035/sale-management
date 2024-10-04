package com.example.myapp.services;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.myapp.domain.dto.admin.collection.CollectionDTO;
import com.example.myapp.domain.dto.admin.collection.CollectionDetailDTO;
import com.example.myapp.domain.dto.admin.collection.CollectionRequestDTO;
import com.example.myapp.exception.ResourceNotFoundException;
import com.example.myapp.mappers.CollectionDTOMapper;
import com.example.myapp.domain.entities.Collection;
import com.example.myapp.domain.entities.Product;
import com.example.myapp.repositories.CollectionRepository;
import com.example.myapp.repositories.ProductRepository;
import com.example.myapp.utils.CodeUtil;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class CollectionService {

	private final CollectionRepository collectionRepository;

	private final ProductRepository productRepository;

	private final CollectionDTOMapper collectionDTOMapper;

	@Transactional(readOnly = true)
	public Page<CollectionDTO> getAllCollections(String filter, Pageable pageable) {
		if (filter != null && !filter.isEmpty()) {
			return collectionRepository.findAllWithFilter(filter, pageable).map(collectionDTOMapper);
		} else {
			return collectionRepository.findAll(pageable).map(collectionDTOMapper);
		}
	}

	@Transactional(readOnly = true)
	public Optional<CollectionDetailDTO> getCollectionById(Integer collectionId) {
		return collectionRepository.findById(collectionId).map(collectionDTOMapper::toCollectionDetailDTO);
	}

	public CollectionDTO createCollection(CollectionRequestDTO request) {
		Collection collection = new Collection();
		BeanUtils.copyProperties(request, collection);
		collection.setCode(CodeUtil.generateCode(request.getName()));
		var newCollection = collectionRepository.save(collection);
		log.debug("Created Information For Collection: {}", request);
		return collectionDTOMapper.apply(newCollection);
	}

	public Optional<CollectionDTO> updateCollection(CollectionRequestDTO updateRequest, Integer collectionId) {
		return Optional.of(collectionRepository.findById(collectionId)).filter(Optional::isPresent).map(Optional::get)
				.map(collection -> {
					boolean changes = false;

					if (updateRequest.getName() != null && !updateRequest.getName().equals(collection.getName())) {
						collection.setName(updateRequest.getName());
						collection.setCode(CodeUtil.generateCode(updateRequest.getName()));
						changes = true;
					}

					if (updateRequest.getDescription() != null
							&& !updateRequest.getDescription().equals(collection.getDescription())) {
						collection.setDescription(updateRequest.getDescription());
						changes = true;
					}

//					if (!changes) {
//						throw new RequestValidationException("no data changes found");
//					}
					log.debug("Changed Information for Collection: {}", collection);
					return collection;
				}).map(collectionDTOMapper);
	}

	public void deleteCollectionById(Integer collectionId) {
		checkIfCollectionExistsOrThrow(collectionId);
		collectionRepository.findById(collectionId).ifPresent(collection -> {
			collectionRepository.delete(collection);
			log.debug("Deleted Collection: {}", collection);
		});

	}

	public void deleteCollections(Integer[] ids) {
		for (Integer id : ids) {
			deleteCollectionById(id);
		}
	}

	private void checkIfCollectionExistsOrThrow(Integer collectionId) {
		if (!collectionRepository.existsById(collectionId)) {
			throw new ResourceNotFoundException(String.format("collection with id [%s] not found", collectionId));
		}
	}

	public void updateProduct(Long[] productIds, Integer collectionId) {
		checkIfCollectionExistsOrThrow(collectionId);
		List<Product> products = productRepository.findAllById(Arrays.asList(productIds));
		collectionRepository.findOneById(collectionId).ifPresent(collection -> {
			if (products != null) {
				collection.setProducts(products);
				collectionRepository.save(collection);
			}
		});
	}

}
