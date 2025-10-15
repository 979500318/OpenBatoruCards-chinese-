package open.batoru.data.cards;

import open.batoru.core.Deck.DeckPosition;
import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.ability.AbilityCondition.ConditionState;
import open.batoru.data.ability.AbilityConst.ActionHint;
import open.batoru.data.ability.modifiers.PowerModifier;

public final class SIGNI_B2_SacabambaspisWaterPhantom extends Card {

    public SIGNI_B2_SacabambaspisWaterPhantom()
    {
        setImageSets("WX24-P4-074");

        setOriginalName("幻水　サカバンバスピス");
        setAltNames("ゲンスイサカバンバスピス Gensui Sakabanbasupisu");
        setDescription("jp",
                "@C：あなたの手札が４枚以上あるかぎり、このシグニのパワーは＋4000される。" +
                "~#：あなたのデッキの上からカードを３枚見る。その中からシグニ１枚を公開し手札に加えるか場に出し、残りを好きな順番でデッキの一番下に置く。"
        );

        setName("en", "Sacabambaspis, Water Phantom");
        setDescription("en",
                "@C: As long as there are 4 or more cards in your hand, this SIGNI gets +4000 power." +
                "~#Look at the top 3 cards of your deck. Reveal 1 SIGNI from among them, and add it to your hand or put it onto the field, and put the rest on the bottom of your deck in any order."
        );

		setName("zh_simplified", "幻水 萨卡班甲鱼");
        setDescription("zh_simplified", 
                "@C :你的手牌在4张以上时，这只精灵的力量+4000。" +
                "~#从你的牌组上面看3张牌。从中把精灵1张公开加入手牌或出场，剩下的任意顺序放置到牌组最下面。\n"
        );

        setCardFlags(CardFlag.LIFEBURST);

        setType(CardType.SIGNI);
        setColor(CardColor.BLUE);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.WATER_BEAST);
        setLevel(2);
        setPower(8000);

        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA);
    }

    @Override
    public IndexedInstance newIndexedInstance(int cardId) { return new IndexedInstance(cardId); }
    public class IndexedInstance extends Card.IndexedInstance
    {
        public IndexedInstance(int cardId)
        {
            super(cardId);
            
            registerConstantAbility(this::onConstEffCond, new PowerModifier(4000));

            registerLifeBurstAbility(this::onLifeBurstEff);
        }
        
        private ConditionState onConstEffCond()
        {
            return getHandCount(getOwner()) >= 4 ? ConditionState.OK : ConditionState.BAD;
        }

        private void onLifeBurstEff()
        {
            look(3);

            CardIndex cardIndex = playerTargetCard(0,1, new TargetFilter().own().SIGNI().fromLooked()).get();
            if(reveal(cardIndex))
            {
                if(playerChoiceAction(ActionHint.HAND, ActionHint.FIELD) == 1)
                {
                    addToHand(cardIndex);
                } else {
                    putOnField(cardIndex);
                }
            }
            
            while(getLookedCount() > 0)
            {
                cardIndex = playerTargetCard(new TargetFilter(TargetHint.BOTTOM).own().fromLooked()).get();
                returnToDeck(cardIndex, DeckPosition.BOTTOM);
            }
        }
    }
}
