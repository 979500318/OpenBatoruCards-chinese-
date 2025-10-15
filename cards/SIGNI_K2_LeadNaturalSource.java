package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.GameConst.CardStateFlag;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.ability.AbilityConst.ActionHint;
import open.batoru.data.ability.AbilityCostList;
import open.batoru.data.ability.cost.DiscardCost;
import open.batoru.data.ability.cost.TrashCost;

public final class SIGNI_K2_LeadNaturalSource extends Card {

    public SIGNI_K2_LeadNaturalSource()
    {
        setImageSets("WXDi-P16-085");

        setOriginalName("羅原　Ｐｂ");
        setAltNames("ラゲンナマリ Ragen Namari Pb Plumbum");
        setDescription("jp",
                "@A @[手札からレベル１のシグニを１枚捨て、このシグニを場からトラッシュに置く]@：あなたのトラッシュからレベル３のシグニ１枚を対象とし、それを場に出す。" +
                "~#：あなたのトラッシュから#Gを持たないレベル２以下のシグニ１枚を対象とし、それを手札に加えるか場に出す。"
        );

        setName("en", "Pb, Natural Element");
        setDescription("en",
                "@A @[Discard a level one SIGNI and put this SIGNI on your field into its owner's trash]@: Put target level three SIGNI from your trash onto your field." +
                "~#Add target level two or less SIGNI without a #G from your trash to your hand or put it onto your field."
        );
        
        setName("en_fan", "Lead, Natural Source");
        setDescription("en_fan",
                "@A @[Discard 1 level 1 SIGNI from your hand, and put this SIGNI from the field into the trash]@: Target 1 level 3 SIGNI from your trash, and put it onto the field." +
                "~#Target 1 level 2 or lower SIGNI without #G @[Guard]@ from your trash, and add it to your hand or put it onto the field."
        );

		setName("zh_simplified", "罗原 Pb");
        setDescription("zh_simplified", 
                "@A 从手牌把等级1的精灵1张舍弃，这只精灵从场上放置到废弃区:从你的废弃区把等级3的精灵1张作为对象，将其出场。" +
                "~#从你的废弃区把不持有#G的等级2以下的精灵1张作为对象，将其加入手牌或出场。\n"
        );

        setCardFlags(CardFlag.LIFEBURST);

        setType(CardType.SIGNI);
        setColor(CardColor.BLACK);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.ATOM);
        setLevel(2);
        setPower(8000);

        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA, PlayFormat.ENGLISH);
    }

    @Override
    public IndexedInstance newIndexedInstance(int cardId) { return new IndexedInstance(cardId); }
    public class IndexedInstance extends Card.IndexedInstance
    {
        public IndexedInstance(int cardId)
        {
            super(cardId);

            registerActionAbility(new AbilityCostList(new DiscardCost(new TargetFilter().SIGNI().withLevel(1)), new TrashCost()), this::onActionEff);

            registerLifeBurstAbility(this::onLifeBurstEff);
        }

        private void onActionEff()
        {
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.FIELD).own().SIGNI().withLevel(3).fromTrash().playable()).get();
            putOnField(target);
        }

        private void onLifeBurstEff()
        {
            CardIndex target = playerTargetCard(new TargetFilter().own().SIGNI().withLevel(0,2).not(new TargetFilter().withState(CardStateFlag.CAN_GUARD)).fromTrash()).get();
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
