package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.ChronoDuration;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.CardColor;
import open.batoru.data.CardConst.CardFlag;
import open.batoru.data.CardConst.CardType;
import open.batoru.data.CardConst.PlayFormat;
import open.batoru.data.ability.SpellAbility;
import open.batoru.data.ability.cost.ExceedCost;

public final class SPELL_K_DarknessSeven extends Card {

    public SPELL_K_DarknessSeven()
    {
        setImageSets("WXDi-P11-083");

        setOriginalName("ダークネス・セブン");
        setAltNames("ダークネスセブン Daakunesu Seban Darkness 7");
        setDescription("jp",
                "このスペルを使用する際、使用コストとして追加でエクシード７を支払ってもよい。\n\n" +
                "対戦相手のシグニ１体を対象とし、ターン終了時まで、それのパワーを－2000する。その後、追加でエクシード７を支払っていた場合、追加で対戦相手のシグニ１体を対象とし、ターン終了時まで、それのパワーを－8000する。" +
                "~#：対戦相手のアップ状態のシグニ１体を対象とし、ターン終了時まで、それのパワーを－15000する。"
        );

        setName("en", "Darkness Seven");
        setDescription("en",
                "As you use this spell, you may pay Exceed 7 as an additional use cost. \n\n" +
                "Target SIGNI on your opponent's field gets --2000 power until end of turn. If you paid the Exceed 7, in addition, target SIGNI on your opponent's field gets --8000 power until end of turn." +
                "~#Target upped SIGNI on your opponent's field gets --15000 power until end of turn."
        );
        
        setName("en_fan", "Darkness Seven");
        setDescription("en_fan",
                "While using this spell, you may pay an additional @[Exceed 7]@ for its use cost.\n\n" +
                "Target 1 of your opponent's SIGNI, and until end of turn, it gets --2000 power. Then, if you paid an additional @[Exceed 7]@, additionally target 1 of your opponent's SIGNI, and until end of turn, it gets --8000 power." +
                "~#Target 1 of your opponent's upped SIGNI, and until end of turn, it gets --15000 power."
        );

		setName("zh_simplified", "乌黑·七");
        setDescription("zh_simplified", 
                "这张魔法使用时，可以作为使用费用追加把超越7支付。（从你的分身的下面把牌合计7张放置到分身废弃区）\n" +
                "对战对手的精灵1只作为对象，直到回合结束时为止，其的力量-2000。追加把超越7支付过的场合，追加对战对手的精灵1只作为对象，直到回合结束时为止，其的力量-8000。" +
                "~#对战对手的竖直状态的精灵1只作为对象，直到回合结束时为止，其的力量-15000。\n"
        );

        setCardFlags(CardFlag.LIFEBURST);

        setType(CardType.SPELL);
        setColor(CardColor.BLACK);

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
            spell.setTargets(playerTargetCard(new TargetFilter(TargetHint.MINUS).OP().SIGNI()));
        }
        private void onSpellEff()
        {
            gainPower(spell.getTarget(), -2000, ChronoDuration.turnEnd());

            if(spell.hasPaidAdditionalCost())
            {
                CardIndex target = playerTargetCard(new TargetFilter(TargetHint.MINUS).OP().SIGNI()).get();
                gainPower(target, -8000, ChronoDuration.turnEnd());
            }
        }

        private void onLifeBurstEff()
        {
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.MINUS).OP().SIGNI().upped()).get();
            gainPower(target, -15000, ChronoDuration.turnEnd());
        }
    }
}
