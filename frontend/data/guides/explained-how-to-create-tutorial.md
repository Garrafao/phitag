---
id: 'explained-how-to-create-tutorial'
title: 'Explained: How to create tutorial phase'
priority: 20
description: 'Here you find out all the necessary details how to create tutorial phase.'
---

## Data Preparation

The most important step of creating a tutorial phase is data preparation. Below, you'll find detailed instructions for preparing the data.
### Usage File

Please provide uses.tsv files in the general format outlined in the [Supported Tasks](./supported-tasks) guide.

### Instance File
The instance file is a tab-separated file with the following columns:

**instanceID**: A unique ID for the instance.

**dataIDs**: A pair of dataIDs, corresponding to the dataID column in the uses.tsv file, for which the lemma is the same.

**label_set**: A scale, e.g. (1,2,3,4).

**non_label**: A non-label (-).

### Judgement File
The judgement file is a tab-separated file with the following columns:

**instanceID**: A unique identifier, matching those in the instance file.

**label**: Contains gold-labeled data. (Note: This data is utilized by the PhiTag System to assess annotator tutorial phases. Ensure it is accurate.)

**comment**: Optional field for additional remarks.

**anotator**: Specifies the annotator's name, e.g. (gold annotator).
# Create Project
The first steps of creating tutorial phase starts with the project. Please create the project first. Here you find out the guide for how to 
[Create Project](./explained-project).

# Create Phase For Tutoral

![Create Phase for Tutorial](/gif/guide/create-tutorial-phase.gif)

# Upload Instance And Judgment File To Tutorial Phase

![Create Phase for Tutorial](/gif/guide/uploaddata.gif)
