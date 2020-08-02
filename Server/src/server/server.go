package server

import (
	"log"
	"net/http"
	"time"
)

func New(addr string, handler *http.ServeMux) *http.Server {
	server := &http.Server{
		Addr:         addr,
		Handler:      handler,
		ReadTimeout:  10 * time.Second,
		WriteTimeout: 10 * time.Second,
		IdleTimeout:  10 * time.Second,
	}
	log.Printf("Start listening on: http://127.0.0.1%v\n", addr)
	return server
}
