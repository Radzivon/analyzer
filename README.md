# analyzer

1) Download project.
2) Configure it.
3) Import colletion postman test https://www.getpostman.com/collections/8a00d7615f2120bc7815
4) Prepare Excel file. The sheet where the data is located have to call 'list'.
5) Start Spring project.
6) In Postman choose test http://localhost:8080/api/upload. Choose tab body. After add Excel a file in the form-data(key: file, value: *.xlsx). Send request.
7) Choose test http://localhost:8080/api/analyze and change parms (fromDate, toDate,merchant). Date format is '20/08/2018T15:00:00'. Send request.
   Example: http://localhost:8080/api/analyze?fromDate=20/08/2018T12:00:00&toDate=20/08/2018T15:00:00&merchant=MacLaren
