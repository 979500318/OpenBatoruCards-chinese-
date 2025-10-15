package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.ChronoDuration;
import open.batoru.core.gameplay.GameConst.GameEventId;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.CardColor;
import open.batoru.data.CardConst.CardFlag;
import open.batoru.data.CardConst.CardType;
import open.batoru.data.CardConst.PlayFormat;
import open.batoru.data.Cost;
import open.batoru.data.ability.AbilityConst.UseLimit;
import open.batoru.data.ability.AutoAbility;
import open.batoru.data.ability.SpellAbility;

public final class SPELL_R_ParkOpeningSignal extends Card {

    public SPELL_R_ParkOpeningSignal()
    {
        setImageSets("WXDi-P12-069");

        setOriginalName("開園の合図");
        setAltNames("カイエンノアイズ Kaien no Aizu");
        setDescription("jp",
                "あなたの#Sのシグニ１体を対象とし、ターン終了時まで、それは@>@U $T1：このシグニがアタックしたとき、対戦相手のパワー10000以下のシグニ１体を対象とし、それをバニッシュする。@@を得る。" +
                "~#：対戦相手のアップ状態のシグニ１体を対象とし、それをバニッシュする。"
        );

        setName("en", "Opening Signal");
        setDescription("en",
                "Target #S SIGNI on your field gains@>@U $T1: When this SIGNI attacks, vanish target SIGNI on your opponent's field with power 10000 or less.@@until end of turn." +
                "~#Vanish target upped SIGNI on your opponent's field."
        );
        
        setName("en_fan", "Park Opening Signal");
        setDescription("en_fan",
                "Target 1 of your #S @[Dissona]@ SIGNI, and until end of turn, it gains:" +
                "@>@U $T1: When this SIGNI attacks, target 1 of your opponent's SIGNI with power 10000 or less, and banish it.@@" +
                "~#Target 1 of your opponent's upped SIGNI, and banish it."
        );

		setName("zh_simplified", "开园的合图");
        setDescription("zh_simplified", 
                "你的#S的精灵1只作为对象，直到回合结束时为止，其得到\n" +
                "@>@U $T1 :当这只精灵攻击时，对战对手的力量10000以下的精灵1只作为对象，将其破坏。@@" +
                "~#对战对手的竖直状态的精灵1只作为对象，将其破坏。\n"
        );

        setCardFlags(CardFlag.LIFEBURST | CardFlag.DISSONA);

        setType(CardType.SPELL);
        setColor(CardColor.RED);
        setCost(Cost.color(CardColor.RED, 1));

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
            spell.setTargets(playerTargetCard(new TargetFilter(TargetHint.ABILITY).own().SIGNI().dissona()));
        }
        private void onSpellEff()
        {
            if(spell.getTarget() != null)
            {
                AutoAbility attachedAuto = new AutoAbility(GameEventId.ATTACK, this::onAttachedAutoEff);
                attachedAuto.setUseLimit(UseLimit.TURN, 1);
                
                attachAbility(spell.getTarget(), attachedAuto, ChronoDuration.turnEnd());
            }
        }
        private void onAttachedAutoEff()
        {
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.BANISH).OP().SIGNI().withPower(0,10000)).get();
            getAbility().getSourceCardIndex().getIndexedInstance().banish(target);
        }

        private void onLifeBurstEff()
        {
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.BANISH).OP().SIGNI().upped()).get();
            banish(target);
        }
    }
}
