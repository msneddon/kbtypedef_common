
##################################################################################
#Additional configuration variables which are pulled from the environment
TOP_DIR = ../..
DEPLOY_RUNTIME ?= /kb/runtime
TARGET ?= /kb/deployment

# including the common makefile gives us a handle to the service directory.  This is
# where we will (for now) dump the service log files
include $(TOP_DIR)/tools/Makefile.common

##################################################################################
# default target is all, which compiles the typespec and builds documentation
all:



##################################################################################
# here are the standard KBase test targets
test: test-client test-scripts

test-all: test-service test-client test-scripts

test-client:

test-scripts:
	
test-service:





##################################################################################
# here are the standard KBase deployment targets
deploy: deploy-all

deploy-all: deploy-client deploy-scripts deploy-service
	
deploy-client: deploy-scripts deploy-docs
	
deploy-scripts:

deploy-docs:
	
deploy-service:

