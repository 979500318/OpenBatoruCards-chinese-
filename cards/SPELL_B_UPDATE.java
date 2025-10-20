package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.ChronoDuration;
import open.batoru.core.gameplay.GameConst.GameEventId;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.Cost;
import open.batoru.data.DataTable;
import open.batoru.data.ability.AbilityConst.ChoiceLogic;
import open.batoru.data.ability.AutoAbility;
import open.batoru.data.ability.SpellAbility;

public final class SPELL_B_UPDATE extends Card {

    public SPELL_B_UPDATE()
    {
        setImageSets("WX24-P1-071");

        setOriginalName("UPDATE");
        setAltNames("アップデート Appudeeto");
        setDescription("jp",
                "あなたの＜電機＞のシグニ１体を対象とし、ターン終了時まで、それは@>@U：このシグニがアタックしたとき、対戦相手のシグニ１体を対象とし、対戦相手が手札を２枚捨てないかぎり、それをバニッシュする。@@を得る。" +
                "~#：対戦相手のシグニを２体まで対象とし、それらをダウンする。"
        );

        setName("en", "UPDATE");
        setDescription("en",
                "Target 1 of your <<Electric Machine>> SIGNI, and until end of turn, it gains:" +
                "@>@U: Whenever this SIGNI attacks, target 1 of your opponent's SIGNI, and banish it unless your opponent discards 2 cards from their hand.@@" +
                "~#Target up to 2 of your opponent's SIGNI, and down them."
        );

		setName("zh_simplified", "UPDATE");
        setDescription("zh_simplified", 
                "你的<<電機>>精灵1只作为对象，直到回合结束时为止，其得到\n" +
                "@>@U :当这只精灵攻击时，对战对手的精灵1只作为对象，如果对战对手不把手牌2张舍弃，那么将其破坏。@@" +
                "~#对战对手的精灵2只最多作为对象，将这些横置。\n"
        );

        setCardFlags(CardFlag.LIFEBURST);

        setType(CardType.SPELL);
        setColor(CardColor.BLUE);
        setCost(Cost.color(CardColor.BLUE, 1) + Cost.colorless(1));

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
            spell.setTargets(playerTargetCard(new TargetFilter(TargetHint.ABILITY).own().SIGNI().withClass(CardSIGNIClass.ELECTRIC_MACHINE)));
        }
        private void onSpellEff()
        {
            if(spell.getTarget() != null)
            {
                AutoAbility attachedAuto = new AutoAbility(GameEventId.ATTACK, this::onAttachedAutoEff);
                attachAbility(spell.getTarget(), attachedAuto, ChronoDuration.turnEnd());
            }
        }
        private void onAttachedAutoEff()
        {
            CardIndex sourceCardIndex = getAbility().getSourceCardIndex();
            
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.BANISH).OP().SIGNI()).get();
            if(target != null && sourceCardIndex.getIndexedInstance().discard(getOpponent(), 0,2, ChoiceLogic.BOOLEAN).size() != 2)
            {
                sourceCardIndex.getIndexedInstance().banish(target);
            }
        }
        
        private void onLifeBurstEff()
        {
            DataTable<CardIndex> data = playerTargetCard(0,2, new TargetFilter(TargetHint.DOWN).OP().SIGNI());
            down(data);
        }
    }
}
