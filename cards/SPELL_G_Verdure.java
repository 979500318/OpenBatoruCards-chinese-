package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.CardColor;
import open.batoru.data.CardConst.CardFlag;
import open.batoru.data.CardConst.CardType;
import open.batoru.data.CardConst.PlayFormat;
import open.batoru.data.Cost;
import open.batoru.data.ability.AbilityConst.ActionHint;

public final class SPELL_G_Verdure extends Card {

    public SPELL_G_Verdure()
    {
        setImageSets("WX24-P4-082");

        setOriginalName("緑光");
        setAltNames("リョッコウ Ryokkou");
        setDescription("jp",
                "【エナチャージ２】をする。その後、あなたの場にレベル４以上のルリグがいる場合、あなたのエナゾーンから緑のシグニを１枚まで対象とし、それを場に出す。" +
                "~#：【エナチャージ１】をする。その後、あなたのエナゾーンからシグニを１枚まで対象とし、それを手札に加えるか場に出す。"
        );

        setName("en", "Verdure");
        setDescription("en",
                "[[Ener Charge 2]]. Then, if there is a level 4 or higher LRIG on your field, target up to 1 green SIGNI from your ener zone, and put it onto the field." +
                "~#[[Ener Charge 1]]. Then, target up to 1 SIGNI from your ener zone, and add it to your hand or put it onto the field."
        );

		setName("zh_simplified", "绿光");
        setDescription("zh_simplified", 
                "[[能量填充2]]。然后，你的场上有等级4以上的分身的场合，从你的能量区把绿色的精灵1张最多作为对象，将其出场。" +
                "~#[[能量填充1]]。然后，从你的能量区把精灵1张最多作为对象，将其加入手牌或出场。\n"
        );

        setCardFlags(CardFlag.LIFEBURST);

        setType(CardType.SPELL);
        setColor(CardColor.GREEN);
        setCost(Cost.color(CardColor.GREEN, 1));

        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA);
    }

    @Override
    public IndexedInstance newIndexedInstance(int cardId) { return new IndexedInstance(cardId); }
    public class IndexedInstance extends Card.IndexedInstance
    {
        public IndexedInstance(int cardId)
        {
            super(cardId);

            registerSpellAbility(this::onSpellEff);

            registerLifeBurstAbility(this::onLifeBurstEff);
        }

        private void onSpellEff()
        {
            enerCharge(2);
            
            if(getLRIG(getOwner()).getIndexedInstance().getLevel().getValue() >= 4)
            {
                CardIndex target = playerTargetCard(0,1, new TargetFilter(TargetHint.FIELD).own().SIGNI().withColor(CardColor.GREEN).fromEner().playable()).get();
                putOnField(target);
            }
        }

        private void onLifeBurstEff()
        {
            enerCharge(1);
            
            CardIndex target = playerTargetCard(0,1, new TargetFilter().own().SIGNI().fromEner()).get();
            
            if(target != null)
            {
                if(playerChoiceAction(ActionHint.HAND, ActionHint.FIELD) == 1)
                {
                    addToHand(target);
                } else {
                    putOnField(target);
                }
            }
        }
    }
}
