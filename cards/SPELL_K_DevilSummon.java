package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.GameConst.CardStateFlag;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.Cost;
import open.batoru.data.ability.AbilityConst.ActionHint;
import open.batoru.data.ability.AbilityConst.ChoiceLogic;
import open.batoru.data.ability.SpellAbility;

import java.util.List;
import java.util.Objects;

public final class SPELL_K_DevilSummon extends Card {

    public SPELL_K_DevilSummon()
    {
        setImageSets("WX24-P1-085");

        setOriginalName("デビル・サモン");
        setAltNames("デビルサモン Debiru Samon");
        setDescription("jp",
                "あなたのトラッシュからそれぞれレベルの異なる＜悪魔＞のシグニ２枚を対象とし、それらを場に出す。" +
                "~#：あなたのトラッシュから#Gを持たないシグニ１枚を対象とし、それを手札に加えるか場に出す。"
        );

        setName("en", "Devil Summon");
        setDescription("en",
                "Target 2 <<Devil>> SIGNI with different levels from your trash, and put them onto the field." +
                "~#Target 1 SIGNI without #G @[Guard]@ from your trash, and add it to your hand or put it onto the field."
        );

		setName("zh_simplified", "恶魔·召唤");
        setDescription("zh_simplified", 
                "从你的废弃区把等级不同的<<悪魔>>精灵2张作为对象，将这些出场。" +
                "~#从你的废弃区把不持有#G的精灵1张作为对象，将其加入手牌或出场。\n"
        );

        setCardFlags(CardFlag.LIFEBURST);

        setType(CardType.SPELL);
        setColor(CardColor.BLACK);
        setCost(Cost.color(CardColor.BLACK, 2));

        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA);
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

            registerLifeBurstAbility(this::onLifeBurstEff);
        }

        private void onSpellEffPreTarget()
        {
            spell.setTargets(playerTargetCard(0,2, ChoiceLogic.BOOLEAN, new TargetFilter(TargetHint.FIELD).own().SIGNI().withClass(CardSIGNIClass.DEVIL).fromTrash(), this::onSpellEffPreTargetCond));
        }
        private boolean onSpellEffPreTargetCond(List<CardIndex> listPickedCards)
        {
            return listPickedCards.size() == 2 && !Objects.equals(listPickedCards.getFirst().getIndexedInstance().getLevel().getValue(), listPickedCards.getLast().getIndexedInstance().getLevel().getValue());
        }
        private void onSpellEff()
        {
            putOnField(spell.getTargets());
        }
        
        private void onLifeBurstEff()
        {
            CardIndex target = playerTargetCard(new TargetFilter().own().SIGNI().not(new TargetFilter().withState(CardStateFlag.CAN_GUARD)).fromTrash()).get();
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
