import React, { useEffect, useState } from 'react';
import {
    DndContext,
    closestCenter,
} from '@dnd-kit/core';
import {
    arrayMove,
    SortableContext,
    verticalListSortingStrategy,
    useSortable,
} from '@dnd-kit/sortable';
import SortableJudgements from './sortablejudgements';
import UseRankJudgement from '../../../../../lib/model/judgement/userankjudgement/model/UseRankJudgement';

interface IProps {

    judgementData: [{
        id: number,
        judgement: UseRankJudgement
    }]
    editFunction: (judgement: UseRankJudgement, label: string) => void;
}
const DraggableJudgemets: React.FC<IProps> = ({ judgementData, editFunction }) => {

    const [dragAndDropData, setDragAndDropData] = useState<{ id: number; rank: string; }[]>([]);

    useEffect(() => {
        if (judgementData) {
            const labelArray = judgementData.map((datas, i) => {
                return datas.judgement.getLabel().split(',').map(item => item.trim());
            }).flat();

            const updatedData = labelArray.map((rank, i) => ({
                id: i,
                rank: rank,
            }));
            setDragAndDropData(updatedData);
        }
    }, [judgementData]);


    const handleDragEnd = (event: any) => {
        if (!event) {
            return;
        }
        const { active, over } = event;
        if (active.id !== over.id) {
            setDragAndDropData((items) => {
                const activeIndex = items.findIndex((item) => item.rank === active.id);
                const overIndex = items.findIndex((item) => item.rank === over.id);

               
                const movedItems = arrayMove(items, activeIndex, overIndex);
                const movedItemsString = movedItems.map((item) => item.rank).join(',');

                let judgement: UseRankJudgement = judgementData[0].judgement;


                editFunction(judgement, movedItemsString);
                
                return movedItems;
            });
        }
    };

    return (
        <DndContext collisionDetection={closestCenter} onDragEnd={handleDragEnd}>
            <div className=" w-auto ">
                <SortableContext
                    items={dragAndDropData.map((item) => item.rank)}
                    strategy={verticalListSortingStrategy}
                >
                    {dragAndDropData.map((judgement, index) => (
                        <SortableJudgements   key={judgement.id} rank={judgement.rank} rankId={judgement.rank.toString()} />
                    ))}
                </SortableContext>
            </div>
        </DndContext>
    );
};

export default DraggableJudgemets;
