package homepage

import (
	"net/http"
	"server/utils"
)

func Init(mux *http.ServeMux) {
	mux.HandleFunc("/", utils.Chain2(Home(), utils.Logging()))
}

func Home() utils.Middleware {
	return func(next utils.MiddlewareFunction) utils.MiddlewareFunction {
		return func(w utils.ResponseWriter, request *http.Request) {
			w.Header().Set("Content-TYpe", "text/plain; charset=utf-8")
			w.WriteHeader(http.StatusOK)
			_, err := w.Write([]byte("Hello, on homepage"))
			if err != nil {
				// TODO: log error
			}
			next(w, request)
		}
	}
}
