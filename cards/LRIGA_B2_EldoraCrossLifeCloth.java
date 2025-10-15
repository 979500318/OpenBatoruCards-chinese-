package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.GameConst.CardLocation;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.Cost;
import open.batoru.data.ability.cost.AddToHandCost;

public final class LRIGA_B2_EldoraCrossLifeCloth extends Card {

    public LRIGA_B2_EldoraCrossLifeCloth()
    {
        setImageSets("WX24-P3-044");

        setOriginalName("エルドラ！クロス・ライフ・クロス！");
        setAltNames("エルドラクロスライフクロス Erudora Kurosu Raifu Kurosu");
        setDescription("jp",
                "@E @[ライフクロス１枚を手札に加える]@：あなたの手札からカードを１枚ライフクロスに加える。"
        );

        setName("en", "Eldora! Cross Life Cloth!");
        setDescription("en",
                "@E @[Add 1 life cloth to hand]@: Add 1 card from your hand to life cloth."
        );

		setName("zh_simplified", "艾尔德拉！交错·生命·护甲！");
        setDescription("zh_simplified", 
                "@E 生命护甲1张加入手牌:从你的手牌把1张牌加入生命护甲。\n"
        );

        setType(CardType.LRIG_ASSIST);
        setLRIGType(CardLRIGType.ELDORA);
        setColor(CardColor.BLUE);
        setCost(Cost.colorless(1));
        setLevel(2);
        setLimit(+1);
        setUseTiming(UseTiming.MAIN);

        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA);
    }

    @Override
    public IndexedInstance newIndexedInstance(int cardId) { return new IndexedInstance(cardId); }
    public class IndexedInstance extends Card.IndexedInstance
    {
        public IndexedInstance(int cardId)
        {
            super(cardId);

            registerEnterAbility(new AddToHandCost(CardLocation.LIFE_CLOTH), this::onEnterEff);
        }
        
        private void onEnterEff()
        {
            CardIndex cardIndex = playerTargetCard(new TargetFilter(TargetHint.HEAL).own().fromHand()).get();
            addToLifeCloth(cardIndex);
        }
    }
}

