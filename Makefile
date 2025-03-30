SHELL = bash

ifneq ("$(wildcard .env)","")
	include .env
endif

.ONESHELL:
setup:
	docker compose up -d
