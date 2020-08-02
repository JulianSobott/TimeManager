package main

import (
	"log"
	"net/http"
	"server/homepage"
	"server/server"
)

func main() {
	mux := http.NewServeMux()
	homepage.Init(mux)
	s := server.New(":8080", mux)
	err := s.ListenAndServe()
	if err != nil {
		log.Fatalf("Server error: %v", err)
	}
}

// log general -> metrics general -> log specific -> metrics -> func
