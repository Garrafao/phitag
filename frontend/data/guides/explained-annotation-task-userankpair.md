---
id: 'explained-annotation-task-userank-pair'
title: 'Explained: Annotation Task - Usage Rank Pair (URANK PAIR)'
priority: 20
description: 'Explanation of the Usage Rank Pair (URANK PAIR) Annotation Task.'
---

# Usage Rank Pair (URANK Pair) Annotation Task

The usage rank pair annotation task asks annotator to rank a set of usages according to the refrence sentence and property. This property should be specified in the guidelines and could be e.g. ambiguity or sentiment.

## Data Format

### Usage File

Please provide uses.tsv files in the general format outlined in the [Supported Tasks](./supported-tasks) guide.

### Instance File
The instance file is a tab-separated file with the following columns:

**instanceID**: A unique ID for the instance.

**dataIDs**: A maximum of 5 pairs of dataIDs, corresponding to the dataID column in the uses.tsv file, for which the lemma is the same. For example: dataID1, dataID2, ..., dataID10. By default, dataID1 and dataID2, dataID3 and dataID4, ..., dataID9 and dataID10 are configured as paired sentences. Please be careful when uploading data.


**label_set**: An ordered set for ranking tasks, e.g., (1, 2, 3, 4, 5), (First Usage, Second Usage, Third Usage, Fourth Usage Fifth Usage), (Rank 1, Rank 2, Rank 3, Rank 4, Rank 5), etc. By default, the first label is assigned to first pair of senetence , the second label to secod pair and so on.

Note: The number of labels should be equivalent to the number of pairs used in instances.

**non_label**: A non-label (-).

## Annotation Process

Your task involves arranging these pair of sentences. For instance, you are presented with several pair sentences. You are asked to arrange these sentences based on the guidelines and the reference sentence, which will be displayed at the top. Utilize a drag-and-drop feature to position them in the correct order. Once you finish placing them, you can click on save to save the judgment.


| ![](/gif/guide/use-rank-pair-annotate.gif) | 
| :-----------------------------------: | 
|    Annotating use rank pair task           |  


Rest of the procedure is same as Use Rank Task. You can refer this [guide](./explained-annotation-task-userank).
