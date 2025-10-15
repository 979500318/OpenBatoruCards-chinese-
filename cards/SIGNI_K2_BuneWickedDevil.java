package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.Cost;
import open.batoru.data.ability.AbilityConst.ActionHint;
import open.batoru.data.ability.AbilityCostList;
import open.batoru.data.ability.cost.DownCost;
import open.batoru.data.ability.cost.EnerCost;

public final class SIGNI_K2_BuneWickedDevil extends Card {

    public SIGNI_K2_BuneWickedDevil()
    {
        setImageSets("WX24-P1-083");

        setOriginalName("凶魔　ブネ");
        setAltNames("キョウマブネ Kyouma Bune");
        setDescription("jp",
                "@A %K #D：あなたのトラッシュから＜悪魔＞のシグニ１枚を対象とし、それを場に出す。" +
                "~#：あなたのトラッシュから＜悪魔＞のシグニ１枚を対象とし、それを手札に加えるか場に出す。"
        );

        setName("en", "Bune, Wicked Devil");
        setDescription("en",
                "@A %K #D: Target 1 <<Devil>> SIGNI from your trash, and put it onto the field." +
                "~#Target 1 <<Devil>> SIGNI from your trash, and add it to your hand or put it onto the field."
        );

		setName("zh_simplified", "凶魔 布涅");
        setDescription("zh_simplified", 
                "@A %K#D:从你的废弃区把<<悪魔>>精灵1张作为对象，将其出场。" +
                "~#从你的废弃区把<<悪魔>>精灵1张作为对象，将其加入手牌或出场。\n"
        );

        setCardFlags(CardFlag.LIFEBURST);

        setType(CardType.SIGNI);
        setColor(CardColor.BLACK);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.DEVIL);
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

            registerActionAbility(new AbilityCostList(new EnerCost(Cost.color(CardColor.BLACK, 1)), new DownCost()), this::onActionEff);

            registerLifeBurstAbility(this::onLifeBurstEff);
        }

        private void onActionEff()
        {
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.FIELD).own().SIGNI().withClass(CardSIGNIClass.DEVIL).fromTrash().playable()).get();
            putOnField(target);
        }

        private void onLifeBurstEff()
        {
            CardIndex target = playerTargetCard(new TargetFilter().own().SIGNI().withClass(CardSIGNIClass.DEVIL).fromTrash()).get();
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
