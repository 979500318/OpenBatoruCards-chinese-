package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.ChronoDuration;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.Cost;
import open.batoru.data.DataTable;
import open.batoru.data.ability.SpellAbility;

public final class SPELL_K_DarkPenetrate extends Card {

    public SPELL_K_DarkPenetrate()
    {
        setImageSets("WX24-P4-089");

        setOriginalName("ダーク・ペネトレイト");
        setAltNames("ダークペネトレイト Daaku Penetoreito");
        setDescription("jp",
                "対戦相手のシグニ１体を対象とし、ターン終了時まで、それのパワーを－7000する。あなたの場にレベル４以上のルリグがいる場合、追加で対戦相手のシグニ１体を対象とし、ターン終了時まで、それのパワーを－2000する。" +
                "~#：対戦相手のアップ状態のシグニ１体を対象とし、ターン終了時まで、それのパワーを－15000する。"
        );

        setName("en", "Dark Penetrate");
        setDescription("en",
                "Target 1 of your opponent's SIGNI, and until end of turn, it gets --7000 power. If there is a level 4 or higher LRIG on your field, additionally target 1 of your opponent's SIGNI, and until end of turn, it gets --2000 power." +
                "~#Target 1 of your opponent's upped SIGNI, and until end of turn, it gets --15000 power."
        );

		setName("zh_simplified", "黑暗·穿透");
        setDescription("zh_simplified", 
                "对战对手的精灵1只作为对象，直到回合结束时为止，其的力量-7000。你的场上有等级4以上的分身的场合，追加对战对手的精灵1只作为对象，直到回合结束时为止，其的力量-2000。" +
                "~#对战对手的竖直状态的精灵1只作为对象，直到回合结束时为止，其的力量-15000。\n"
        );

        setCardFlags(CardFlag.LIFEBURST);

        setType(CardType.SPELL);
        setColor(CardColor.BLACK);
        setCost(Cost.color(CardColor.BLACK, 1));

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
            DataTable<CardIndex> targets = playerTargetCard(new TargetFilter(TargetHint.MINUS).OP().SIGNI());
            if(getLRIG(getOwner()).getIndexedInstance().getLevel().getValue() >= 4) targets.add(playerTargetCard(new TargetFilter(TargetHint.MINUS).OP().SIGNI()).get());
            spell.setTargets(targets);
        }
        private void onSpellEff()
        {
            gainPower(spell.getTarget(0), -7000, ChronoDuration.turnEnd());
            gainPower(spell.getTarget(1), -2000, ChronoDuration.turnEnd());
        }

        private void onLifeBurstEff()
        {
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.MINUS).OP().SIGNI().upped()).get();
            gainPower(target, -15000, ChronoDuration.turnEnd());
        }
    }
}
