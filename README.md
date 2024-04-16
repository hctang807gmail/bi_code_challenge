# bi_code_challenge
A command line tools for importing different format of CSV and displaying some aggregated data

## Prerequisites
- Docker engine

## Configuration
- <project_folder>/app/upload
  - This folder will be mounted into the docker container. Files in this folder will be consumed during import.
- <project_folder>/app/config/dataMapping.json
  - Configuration file defined the core fields and mapping of csv column from different data source
 
## Usage
- Download the project
- cd <project_folder>
- start.bat / start.sh (depends on platform)

## Todo
- Enhance error handling
- Enrich Unit Testing

