---
id: 'explained-annotation-task-userank'
title: 'Explained: Annotation Task - Usage Rank (URANK)'
priority: 20
description: 'Explanation of the Usage Rank (URANK) Annotation Task.'
---

# Usage Rank Annotation (URANK) Annotation Task

The usage rank annotation task asks annotator to rank a set of usages according to some property. This property should be specified in the guidelines and could be e.g. ambiguity or sentiment.

## Data Format

### Usage File

Please provide uses.tsv files in the general format outlined in the [Supported Tasks](/guides/supported-tasks) guide.

### Instance File
The instance file is a tab-separated file with the following columns:

**instanceID**: A unique ID for the instance.

**dataIDs**: A pair of dataIDs, corresponding to the dataID column in the uses.tsv file, for which the lemma is the same.

**label_set**: An ordered set for ranking tasks e.g. (1, 2, 3, 4), (First Usage, Second Usage, Third Usage, Fourth Usage), (Rank 1, Rank 2, Rank 3, Rank 4), etc., where by default, the first label is assigned to Usage 1, the second label to Usage 2, the third to Usage 3, and the fourth label to Usage 4.

**non_label**: A non-label (-).

## Annotation Process

Your task involves arranging these sentences. For instance, you are presented with four sentences, as shown in the video below. You are asked to arrange these sentences based on the guidelines or target text, which will be displayed at the top. Utilize a drag-and-drop feature to position them in correct order. Once you finish placing you can click on save to save the judgement.

| ![](/gif/guide/annotate-use-rank.gif) | 
| :-----------------------------------: | 
|    Annotating use rank task           |  




***

The non_label symbol '-' is used when the annotator is unable to make a judgment. Please use this option only if absolutely necessary, i.e., if you cannot make a decision about the order of these sentences. This may be the case.
| ![](/gif/guide/annotatate-non-label-use-rank.gif) |  
| :-----------------------------------------------: | 
|    Assigning non label judgement                  |      



## Editing Judgement
Navigate to the "Judgment" tab section for editing. In the "Rank" column, you'll find the judgments. Simply rearrange them by dragging and dropping into the correct order, and your edited judgment will be automatically saved.

| ![](/gif/guide/edit-use-rank.gif)     | 
| :-----------------------------------: | 
|    Editing the judgement              |  

## Adding Label to Non-Label Judgement

To assign a label to a non-labeled judgment, go to the "Judgment" tab, click on the edit judgment icon, then select the "Add Label" button. This will provide labels for non-labeled judgments in ascending order. You can easily rearrange them by dragging and dropping to achieve the correct order.

| ![](/gif/guide/add-label-to-use-rank.gif)     | 
| :-----------------------------------: | 
|    Adding label to non-label task     |              |  






