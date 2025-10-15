package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.ability.Ability.AbilityFlag;
import open.batoru.data.ability.EnterAbility;

public final class LRIGA_G2_AyaneOkusoraSwimsuit extends Card {

    public LRIGA_G2_AyaneOkusoraSwimsuit()
    {
        setImageSets("WXDi-CP02-041");
        setLinkedImageSets("WXDi-CP02-TK02A");

        setOriginalName("奥空アヤネ(水着)");
        setAltNames("オクソラアヤネミズギ Okusora Ayane Mizugi");
        setDescription("jp",
                "@E：クラフトの《雨雲号》１つを場に出す。" +
                "~{{E：【エナチャージ１】をする。その後、あなたのエナゾーンから＜ブルアカ＞のカードを１枚まで対象とし、それを手札に加える。"
        );

        setName("en", "Okusora Ayane (Swimsuit)");
        setDescription("en",
                "@E: Put a \"Water Cloud\" Craft onto your field.~{{E: [[Ener Charge 1]]. Then, add up to one target <<Blue Archive>> card from your Ener Zone to your hand."
        );
        
        setName("en_fan", "Ayane Okusora (Swimsuit)");
        setDescription("en_fan",
                "@E: Put 1 \"Nimbostratus\" craft onto the field." +
                "~{{E: [[Ener Charge 1]]. Then, target up to 1 <<Blue Archive>> card from your ener zone, and add it to your hand."
        );

		setName("zh_simplified", "奥空绫音(泳装)");
        setDescription("zh_simplified", 
                "@E :衍生的《雨雲号》1只出场。\n" +
                "~{{E:[[能量填充1]]。然后，从你的能量区把<<ブルアカ>>牌1张最多作为对象，将其加入手牌。@@\n"
        );

        setType(CardType.LRIG_ASSIST);
        setLRIGType(CardLRIGType.FORECLOSURE_TASK_FORCE);
        setColor(CardColor.GREEN);
        setLevel(2);
        setLimit(+1);
        setUseTiming(UseTiming.MAIN);

        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA, PlayFormat.ENGLISH);
    }

    @Override
    public IndexedInstance newIndexedInstance(int cardId) { return new IndexedInstance(cardId); }
    public class IndexedInstance extends Card.IndexedInstance
    {
        public IndexedInstance(int cardId)
        {
            super(cardId);

            registerEnterAbility(this::onEnterEff1);

            EnterAbility enter2 = registerEnterAbility(this::onEnterEff2);
            enter2.getFlags().addValue(AbilityFlag.BONDED);
        }

        private void onEnterEff1()
        {
            if(new TargetFilter().own().SIGNI().zone().playable().getValidTargetsCount() > 0)
            {
                CardIndex cardIndex = craft(getLinkedImageSets().get(0));
                
                if(!putOnField(cardIndex))
                {
                    exclude(cardIndex);
                }
            }
        }

        private void onEnterEff2()
        {
            enerCharge(1);

            CardIndex target = playerTargetCard(0,1, new TargetFilter(TargetHint.HAND).own().withClass(CardSIGNIClass.BLUE_ARCHIVE).fromEner()).get();
            addToHand(target);
        }
    }
}

