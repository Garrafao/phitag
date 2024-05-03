---
id: 'explained-annotation-task-userank-relative'
title: 'Explained: Annotation Task - Usage Rank Relative (URANK)'
priority: 20
description: 'Explanation of the Usage Rank Relative (URANK Relative) Annotation Task.'
---

# Usage Rank Relative (URANK Relative) Annotation Task

The usage rank relative annotation task asks annotator to rank a set of usages according to the refrence sentence and property. This property should be specified in the guidelines and could be e.g. ambiguity or sentiment.

## Data Format

### Usage File

Please provide uses.tsv files in the general format outlined in the [Supported Tasks](./supported-tasks) guide.

### Instance File
The instance file is a tab-separated file with the following columns:

**instanceID**: A unique ID for the instance.

**dataIDs**: A maximum of 10 datIDs, corresponding to the dataID column in the uses.tsv file, for which the lemma is the same. For example: dataID1, dataID2, ..., dataID10. By default, dataID1 is configured as a reference sentence.


**label_set**: An ordered set for ranking tasks, e.g., (1, 2, 3, 4,.....10), (First Usage, Second Usage, Third Usage, Fourth Usage,...., Tenth Usage), (Rank 1, Rank 2, Rank 3, Rank 4,......., Rank10), etc. By default, the first label is assigned to Usage 1, the second label to Usage 2, the third to Usage 3, and the fourth label to Usage 4.
Note: The number of labels should be equivalent to the number of dataIDs used in instances.

**non_label**: A non-label (-).

## Annotation Process

Your task involves arranging these sentences. For instance, you are presented with several sentences, with the first sentence serving as your reference sentence, as shown in the video below. You are asked to arrange these sentences based on the guidelines and the reference sentence, which will be displayed at the top. Utilize a drag-and-drop feature to position them in the correct order. Once you finish placing them, you can click on save to save the judgment.


| ![](/gif/guide/use-rank-realtive-annotate.gif) | 
| :-----------------------------------: | 
|    Annotating use rank relative task           |  


Rest of the procedure is same as Use Rank Task. You can refer this [guide](./explained-annotation-task-userank).
