package homepage

import (
	"net/http"
)

func Init(mux *http.ServeMux) {
	mux.HandleFunc("/", home)
}

func home(writer http.ResponseWriter, request *http.Request) {
	http.ServeFile(writer, request, "public/website/index.html")
}
