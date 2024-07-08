---
id: 'explained-annotation-task-wssim'
title: 'Explained: Annotation Task - Text Label'
priority: 20
description: 'Explanation of the Text Label Annotation Task.'
---

# Text Label Annotation Task

In the Text Label annotation task, users are shown a target lemma in context as well as a sense which may or may not apply to the target lemma. Annotators must rate the sense on how well it matches the meaning of the target lemma in its given context.


## Data Format

### Usage File

Please provide uses.tsv files in the general format outlined in the [Supported Tasks](/guide/supported-tasks) guide.

### Instance File

There are two types of instance files for the WSSIM task: one containing the senses and one containing the instances. The senses file (also called tag file) is a tab-separated file with the following columns:

**senseID**: A unique ID for the sense.

**definition**: The sense definition.

**lemma**: The target lemma.

The instances file is a tab-separated file with the following columns:

**instanceID**: A unique ID for the instance.

**dataIDs**: A data ID corresponding to the the dataID in uses and a senseID from the senses.tsv file.

**label_set**: A scale, e.g. (1,2,3,4,5) or binary (0,1).

**non_label**: Empty non-label

## Annotation Process

Your task is to rate the sense on how well it matches the meaning of the target lemma in its given context. Please note that there is no right or wrong answer. We just want your opinion for each item on this task.

You may leave comments during the annotation task.

### Annotation Examples

#### Example 1

The following example shows a sense annotated with a scale of 1-5.

| This is also at the very essence or heart of being a **coach**. |
|-------------------------|
|Sense: **a carriage pulled by four horses with one driver**|
|Rating: **1**|

| This is also at the very essence or heart of being a **coach**. |
|-------------------------|
|Sense: **(sports) someone in charge of training an athlete or a team**|
|Rating: **4**|

| This is also at the very essence or heart of being a **coach**. |
|-------------------------|
|Sense: **a person who gives private instruction (as in singing, acting, etc.)**|
|Rating: **3**|

#### Example 2

The following example shows a sense annotated with a binary scale, also called WSBest (Word Sense Best).

|The **bank** was closed. |
|-------------------------|
|Sense: **financial institution**|
|Rating: **1**|

|The **bank** was closed. |
|-------------------------|
|Sense: **side of a river**|
|Rating: **0**|


## Visual Example

### WSSIM 

![Annotation Example](/gif/guide/annotate-wssim.gif)

### WSBest

![Annotation Example](/gif/guide/annotate-wsbest.gif)