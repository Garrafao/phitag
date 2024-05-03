---
id: 'explained-how-to-use-gpt-annotator'
title: 'Explained: How to use GPT compuatational annotator'
priority: 20
description: 'Here you find out all the necessary details how to use ChatGPT as an annotator.'
---

# Using GPT as a computational annotator

This guide explain the process of employing GPT as a computational annotator within a PhiTag.


## Step 1
Initiating GPT as an annotator begins with data preparation.

## Data Format

### Usage File

Please provide uses.tsv files in the general format outlined in the [Supported Tasks](/guides/supported-tasks) guide.

### Instance File
The instance file is a tab-separated file with the following columns:

**instanceID**: A unique ID for the instance.

**dataIDs**: A pair of dataIDs, corresponding to the dataID column in the uses.tsv file, for which the lemma is the same.

**label_set**: A scale, e.g. (1,2,3,4).

**non_label**: A non-label (-).

## Step 2: Project Creation
Follow this guideline [Explained: Project](/guide/explained-project) to create a project in PhiTag.


## Step 3: Phase Create
Follow this guideline [Explained: Phase](/guide/explained-phase) to create a project in PhiTag.

## Step 4: Add ChatGPT as a computaional annotator to your project

| ![](/gif/guide/add-comp-annotator.gif) |
| :---------------------------------: |
|           Add GPT as a computational annotator          |


## Step 5: Annotate Phase
Here's a visual representation of how to utilize GPT as an annotator. You have the option to either use your own custom prompt or leverage the automatic prompting feature. The automatic prompting feature utilizes guideline and guideline + tutorial as prompts automatically.

| ![](/gif/guide/annotate-phase-using-gpt.gif) |
| :---------------------------------:    |
|    Annotate Phase using GPT      |


## Test model with tutorail Phase
To evaluate the model's accuracy using the tutorial phase, you need to first create a tutorial phase. Follow the guidelines provided in
    [Explained: Create Tutorial Phase](/guide/explained-how-to-create-tutorial)


| ![](/gif/guide/test-with-tutorial.gif)  |
| :-------------------------------------------: |
|    Test model with tutorial               |






