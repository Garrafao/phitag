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
import Usage from '../../../../../lib/model/phitagdata/usage/model/Usage';
import SortableUseRankPairUsage from './sortableuserankpairussage';

interface UsageFieldContainerProps {
  usages: {
    key: string;
    usage1: Usage;
    usage2: Usage;
  }[];
  handleUsagesReordered: Function;
  containerSize: Number;
}

const DraggableUseRankPairUsage: React.FC<UsageFieldContainerProps> = ({ usages, handleUsagesReordered, containerSize }) => {
  const [orderedUsages, setOrderedUsages] = useState(usages);
  const [containerHeight, setContainerHeight] = useState(containerSize);


  const handleDragEnd = (event: any) => {
    if (!event) {
      return;
    }

    const { active, over } = event;

    if (active.id !== over.id) {
      setOrderedUsages((items) => {
        const activeIndex = items.findIndex((item) => item.key === active.id);
        const overIndex = items.findIndex((item) => item.key === over.id);

        const movedItems = arrayMove(items, activeIndex, overIndex);
        const movedItemsString = movedItems.map((item) => item.key).join(',');

        handleUsagesReordered(movedItemsString);

        return movedItems;
      });
    }
  };
  useEffect(() => {
    if (containerSize) {
      setContainerHeight(containerSize);
    }
  }, [containerSize]);

  return (
    <DndContext collisionDetection={closestCenter} onDragEnd={handleDragEnd}>
      <div className="p-3" style={{ width: '100%', height: `${containerHeight}px`, overflowY: 'auto' }}>

        <SortableContext items={orderedUsages.map((item) => item.key)} strategy={verticalListSortingStrategy}>
          {orderedUsages.map((usage, index) => (
            <SortableUseRankPairUsage
              key={usage.key}
              usage={{
                usage1: usage.usage1,
                usage2: usage.usage2,
              }}
              id={usage.key}
            />
          ))}
        </SortableContext>
      </div>
    </DndContext>
  );
};

export default DraggableUseRankPairUsage;
