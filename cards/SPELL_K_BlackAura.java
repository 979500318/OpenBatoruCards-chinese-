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

public final class SPELL_K_BlackAura extends Card {

    public SPELL_K_BlackAura()
    {
        setImageSets("WXDi-P12-089");

        setOriginalName("ブラック・オーラ");
        setAltNames("ブラックオーラ Burakku Oora");
        setDescription("jp",
                "対戦相手のシグニ１体を対象とし、ターン終了時まで、それのパワーをあなたの場にある#Sのシグニ１体につき－1000する。" +
                "~#：対戦相手のアップ状態のシグニ１体を対象とし、ターン終了時まで、それのパワーを－15000する。"
        );

        setName("en", "Black Aura");
        setDescription("en",
                "Target SIGNI on your opponent's field gets --1000 power for each #S SIGNI on your field until end of turn. \n" +
                "~#Target upped SIGNI on your opponent's field gets --15000 power until end of turn."
        );
        
        setName("en_fan", "Black Aura");
        setDescription("en_fan",
                "Target 1 of your opponent's SIGNI, and until end of turn, it gets --1000 power for each of your #S @[Dissona]@ SIGNI." +
                "~#Target 1 of your opponent's upped SIGNI, and until end of turn, it gets --15000 power."
        );

		setName("zh_simplified", "漆黑·暗气");
        setDescription("zh_simplified", 
                "对战对手的精灵1只作为对象，直到回合结束时为止，其的力量依据你的场上的#S的精灵的数量，每有1只就-1000。" +
                "~#对战对手的竖直状态的精灵1只作为对象，直到回合结束时为止，其的力量-15000。\n"
        );

        setCardFlags(CardFlag.LIFEBURST | CardFlag.DISSONA);

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

            registerLifeBurstAbility(this::onLifeBurstEff);
        }
        
        private void onSpellEffPreTarget()
        {
            spell.setTargets(playerTargetCard(new TargetFilter(TargetHint.MINUS).OP().SIGNI()));
        }
        private void onSpellEff()
        {
            gainPower(spell.getTarget(), -1000 * new TargetFilter().own().SIGNI().dissona().getValidTargetsCount(), ChronoDuration.turnEnd());
        }

        private void onLifeBurstEff()
        {
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.MINUS).OP().SIGNI().upped()).get();
            gainPower(target, -15000, ChronoDuration.turnEnd());
        }
    }
}
