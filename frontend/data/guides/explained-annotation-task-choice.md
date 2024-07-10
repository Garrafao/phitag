---
id: 'explained-annotation-task-choice'
title: 'Explained: Annotation Task - Text Choice'
priority: 20
description: 'Explanation of the Text Choice Annotation Task.'
---
## Introduction

In text choice annotation task, annotators are asked to select the correct option from a set of given choices based on a specific property. This property should be given in the guidelines or the task head.

## Data Format
Please provide uses.tsv file in the general format outlined in the [Supported Tasks](/guide/supported-tasks) guide.
### instances.tsv
**instanceID**: A unique ID for the instance.

**dataIDs**: A dataID corresponding to a text to be annotated. 

**label_set**: A set of choices such as Option 1, Option 2, Option 3, Option 4, etc.

**non_label**: A non-label (-). 

## Annotation Process
The annotator is asked to select one option from the given set of choices.
For example, consider the following text:

| Text| 
|--------------------------------------------------------|
|On a scale of 1 to 5, how satisfied are you with your recent shopping experience"|


## Choices
- 5 Very Satisifed
- 4 Somewhat Satisfied
- 3 Neutral
- 2 Somewhat Dissatisfied
- 1 Very Dissatisfied
- \- Can't decide


Choose your desired option. If you want to change your selection, you can always edit your choice in the phase judgement tab.