package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.ChronoDuration;
import open.batoru.core.gameplay.GameConst.CardUnderType;
import open.batoru.core.gameplay.GameConst.GameEventId;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.Cost;
import open.batoru.data.ability.AutoAbility;
import open.batoru.data.ability.SpellAbility;

public final class SPELL_K_BeetleBlade extends Card {

    public SPELL_K_BeetleBlade()
    {
        setImageSets("WX25-P2-109");

        setOriginalName("ビートル・ブレード");
        setAltNames("ビートルブレード Biitoru Bureedo");
        setDescription("jp",
                "あなたの＜凶蟲＞のシグニ１体を対象とし、ターン終了時まで、それは@>@U：このシグニがアタックしたとき、対戦相手のシグニ１体を対象とし、ターン終了時まで、それのパワーを－8000する。それに【チャーム】が付いている場合、代わりにターン終了時まで、それのパワーを－10000する。@@を得る。" +
                "~#：対戦相手のアップ状態のシグニ１体を対象とし、ターン終了時まで、それのパワーを－15000する。"
        );

        setName("en", "Beetle Blade");
        setDescription("en",
                "Target 1 of your <<Misfortune Insect>> SIGNI, and until end of turn, it gains:" +
                "@>@U: Whenever this SIGNI attacks, target 1 of your opponent's SIGNI, and until end of turn, it gets --8000 power. If it has a [[Charm]] attached to it, until end of turn, it gets --10000 power instead.@@" +
                "~#Target 1 of your opponent's upped SIGNI, and until end of turn, it gets --15000 power."
        );

		setName("zh_simplified", "甲虫·刀刃");
        setDescription("zh_simplified", 
                "你的<<凶蟲>>精灵1只作为对象，直到回合结束时为止，其得到\n" +
                "@>@U :当这只精灵攻击时，对战对手的精灵1只作为对象，直到回合结束时为止，其的力量-8000。其有[[魅饰]]附加的场合，作为替代，直到回合结束时为止，其的力量-10000。@@" +
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
            spell.setTargets(playerTargetCard(new TargetFilter(TargetHint.ABILITY).own().SIGNI().withClass(CardSIGNIClass.MISFORTUNE_INSECT)));
        }
        private void onSpellEff()
        {
            CardIndex target = spell.getTarget();
            if(target != null)
            {
                AutoAbility attachedAuto = new AutoAbility(GameEventId.ATTACK, this::onAttachedAutoEff);
                attachAbility(target, attachedAuto, ChronoDuration.turnEnd());
            }
        }
        private void onAttachedAutoEff()
        {
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.MINUS).OP().SIGNI()).get();
            
            if(target != null)
            {
                getAbility().getSourceCardIndex().getIndexedInstance().gainPower(target, target.getIndexedInstance().getCardsUnderCount(CardUnderType.ATTACHED_CHARM) == 0 ? -8000 : -10000, ChronoDuration.turnEnd());
            }
        }

        private void onLifeBurstEff()
        {
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.MINUS).OP().SIGNI().upped()).get();
            gainPower(target, -15000, ChronoDuration.turnEnd());
        }
    }
}
