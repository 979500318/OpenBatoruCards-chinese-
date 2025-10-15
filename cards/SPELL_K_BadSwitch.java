package open.batoru.data.cards;

import open.batoru.core.gameplay.ChronoDuration;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.CardColor;
import open.batoru.data.CardConst.CardType;
import open.batoru.data.CardConst.PlayFormat;
import open.batoru.data.Cost;
import open.batoru.data.ability.SpellAbility;

public final class SPELL_K_BadSwitch extends Card {

    public SPELL_K_BadSwitch()
    {
        setImageSets("WXK01-110");

        setOriginalName("バッド・スイッチ");
        setAltNames("バッドスイッチ Baddo Suicchi");
        setDescription("jp",
                "以下の２つから１つを選ぶ。\n" +
                "$$1あなたのトラッシュから黒のシグニ１枚を対象とし、それを手札に加える。\n" +
                "$$2対戦相手のシグニ１体を対象とし、ターン終了時まで、それのパワーを－7000する。"
        );

        setName("en", "Bad Switch");
        setDescription("en",
                "@[@|Choose 1 of the following:|@]@\n" +
                "$$1 Target 1 black SIGNI from your trash, and add it to your hand.\n" +
                "$$2 Target 1 of your opponent's SIGNI, and until end of turn, it gets --7000 power."
        );

		setName("zh_simplified", "恶性·开关");
        setDescription("zh_simplified", 
                "从以下的2种选1种。\n" +
                "$$1 从你的废弃区把黑色的精灵1张作为对象，将其加入手牌。\n" +
                "$$2 对战对手的精灵1只作为对象，直到回合结束时为止，其的力量-7000。\n"
        );

        setType(CardType.SPELL);
        setColor(CardColor.BLACK);
        setCost(Cost.color(CardColor.BLACK, 1));

        setPlayFormat(PlayFormat.KEY);
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
            spell.setModeChoice(1);
        }
        private void onSpellEffPreTarget()
        {
            if(spell.getChosenModes() == 1)
            {
                spell.setTargets(playerTargetCard(new TargetFilter(TargetHint.HAND).own().SIGNI().withColor(CardColor.BLACK).fromTrash()));
            } else {
                spell.setTargets(playerTargetCard(new TargetFilter(TargetHint.MINUS).OP().SIGNI()));
            }
        }
        private void onSpellEff()
        {
            if(spell.getChosenModes() == 1)
            {
                addToHand(spell.getTarget());
            } else {
                gainPower(spell.getTarget(), -7000, ChronoDuration.turnEnd());
            }
        }
    }
}
