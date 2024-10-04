import { HttpHeaders, HttpParams } from '@angular/common/http';

export const createRequestOption = (req?: any): HttpParams => {
  let options: HttpParams = new HttpParams();

  if (req) {
    Object.keys(req).forEach(key => {
      if (key !== 'sort' && req[key]) {
        for (const value of [].concat(req[key]).filter(v => v !== '')) {
          options = options.append(key, value);
        }
      }
    });

    if (req.sort) {
      req.sort.forEach((val: string) => {
        options = options.append('sort', val);
      });
    }
  }

  return options;
};

export const createFormData = (file: File):FormData => {
  const formData = new FormData();
  formData.append('file', file);
  return formData;
}

export const createHttpOptions = (data: any[]): any => {
  return {
    headers: new HttpHeaders({
      'Content-Type': 'application/json',
    }),
    body: data,
    // body: JSON.stringify(data)
  };
}
