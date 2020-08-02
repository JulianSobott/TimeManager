package server

import (
	"log"
	"net/http"
	"time"
)

func New(addr string, handler *http.ServeMux) *http.Server {
	h := &generalHandler{handler}
	server := &http.Server{
		Addr:         addr,
		Handler:      h,
		ReadTimeout:  10 * time.Second,
		WriteTimeout: 10 * time.Second,
		IdleTimeout:  10 * time.Second,
	}
	log.Printf("Start listening on: http://127.0.0.1%v\n", addr)
	return server
}

type generalHandler struct {
	mux *http.ServeMux
}

func (h *generalHandler) ServeHTTP(w http.ResponseWriter, r *http.Request) {
	// Prepare
	writer := NewWrapperResponseWriter(w)

	// Log
	start := time.Now()
	defer func() {
		http.StatusText(writer.StatusCode)
		log.Printf("%s %s -> %s %.2fs",
			r.Method, r.URL, http.StatusText(writer.StatusCode), time.Since(start).Seconds())
	}()

	// Metrics

	// Handle
	h.mux.ServeHTTP(&writer, r)
}

type ResponseWriter struct {
	http.ResponseWriter
	StatusCode int
}

func NewWrapperResponseWriter(w http.ResponseWriter) ResponseWriter {
	return ResponseWriter{
		w, http.StatusOK,
	}
}

func (w *ResponseWriter) WriteHeader(statusCode int) {
	w.ResponseWriter.WriteHeader(statusCode)
	w.StatusCode = statusCode
}
