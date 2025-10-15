package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.data.DataTable;
import open.batoru.core.gameplay.GameConst.CardStateFlag;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.CardColor;
import open.batoru.data.CardConst.CardFlag;
import open.batoru.data.CardConst.CardType;
import open.batoru.data.CardConst.PlayFormat;
import open.batoru.data.ability.AbilityConst.ActionHint;
import open.batoru.data.ability.SpellAbility;
import open.batoru.data.ability.cost.ExceedCost;

public final class SPELL_G_BenefitingFromAMaster extends Card {

    public SPELL_G_BenefitingFromAMaster()
    {
        setImageSets("WXDi-P11-076");

        setOriginalName("七光");
        setAltNames("ナナヒカリ Nana Hikari Seven Lights 7");
        setDescription("jp",
                "このスペルを使用する際、使用コストとして追加でエクシード７を支払ってもよい。\n\n" +
                "あなたのトラッシュからあなたのセンタールリグと共通する色を持つシグニ１枚を対象とし、それをエナゾーンに置く。その後、追加でエクシード７を支払っていた場合、あなたのエナゾーンから[ガード]を持たないシグニを２枚まで対象とし、それらを手札に加える。" +
                "~#：【エナチャージ１】をする。その後、あなたのエナゾーンからシグニを１枚まで対象とし、それを手札に加えるか場に出す。"
        );

        setName("en", "Seven Lights");
        setDescription("en",
                "As you use this spell, you may pay Exceed 7 as an additional use cost. \n\n" +
                "Put target SIGNI that shares a color with your Center LRIG from your trash into your Ener Zone. Then, if you paid the Exceed 7, add up to two target SIGNI without a #G from your Ener Zone to your hand." +
                "~#[[Ener Charge 1]]. Then, add up to one target SIGNI from your Ener Zone to your hand or put it onto your field."
        );
        
        setName("en_fan", "Benefiting from a Master");
        setDescription("en_fan",
                "While using this spell, you may pay an additional @[Exceed 7]@ for its use cost.\n\n" +
                "Target 1 SIGNI that shares a common color with your center LRIG from your trash, and put it into the ener zone. Then, if you paid an additional @[Exceed 7]@, target up to 2 SIGNI without #G @[Guard]@ from your ener zone, add add them to your hand." +
                "~#[[Ener Charge 1]]. Then, target up to 1 SIGNI from your ener zone, and add it to your hand or put it onto the field."
        );

		setName("zh_simplified", "七光");
        setDescription("zh_simplified", 
                "这张魔法使用时，可以作为使用费用追加把超越7支付。\n" +
                "从你的废弃区把持有与你的核心分身共通颜色的精灵1张作为对象，将其放置到能量区。然后，追加把超越7支付过的场合，从你的能量区把不持有#G的精灵2张最多作为对象，将这些加入手牌。" +
                "~#[[能量填充1]]。然后，从你的能量区把精灵1张最多作为对象，将其加入手牌或出场。\n"
        );

        setCardFlags(CardFlag.LIFEBURST);

        setType(CardType.SPELL);
        setColor(CardColor.GREEN);

        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA, PlayFormat.ENGLISH, PlayFormat.CHINESE);
    }

    @Override
    public IndexedInstance newIndexedInstance(int cardId) { return new IndexedInstance(cardId); }
    public class IndexedInstance extends Card.IndexedInstance
    {
        private final SpellAbility spell;
        public IndexedInstance(int cardId)
        {
            super(cardId);

            spell = registerSpellAbility(this::onSpellEffPreTarget, this::onSpellEff);
            spell.setAdditionalCost(new ExceedCost(7));

            registerLifeBurstAbility(this::onLifeBurstEff);
        }

        private void onSpellEffPreTarget()
        {
            spell.setTargets(playerTargetCard(new TargetFilter(TargetHint.ENER).own().SIGNI().withColor(getLRIG(getOwner()).getIndexedInstance().getColor()).fromTrash()));
        }
        private void onSpellEff()
        {
            putInEner(spell.getTarget());
            
            if(spell.hasPaidAdditionalCost())
            {
                DataTable<CardIndex> data = playerTargetCard(0,2, new TargetFilter(TargetHint.HAND).own().SIGNI().not(new TargetFilter().withState(CardStateFlag.CAN_GUARD)).fromEner());
                addToHand(data);
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
