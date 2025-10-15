package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.ChronoDuration;
import open.batoru.core.gameplay.GameConst.GameEventId;
import open.batoru.core.gameplay.GameConst.UseCondition;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.CardColor;
import open.batoru.data.CardConst.CardType;
import open.batoru.data.CardConst.PlayFormat;
import open.batoru.data.Cost;
import open.batoru.data.ability.AutoAbility;
import open.batoru.data.ability.SpellAbility;

public final class SPELL_R_ExcavationOfTheElectricHorse extends Card {
    
    public SPELL_R_ExcavationOfTheElectricHorse()
    {
        setImageSets("WXDi-P06-059");
        
        setOriginalName("電動馬の発掘");
        setAltNames("デンドウバノハックツ Dendouba no Hakkutsu");
        setDescription("jp",
                "以下の２つから１つを選ぶ。\n" +
                "$$1&Rを持つあなたのシグニの下にある赤のシグニ１枚を対象とし、それを手札に加える。\n" +
                "$$2&Rを持つあなたのシグニ１体を対象とし、ターン終了時まで、それのパワーを＋3000し、それは@>@U：このシグニがアタックしたとき、自身のパワー以下の対戦相手のシグニ１体を対象とし、%X %Xを支払ってもよい。そうした場合、それをバニッシュする。@@を得る。"
        );
        
        setName("en", "E - Lectrobike Revealed");
        setDescription("en",
                "Choose one of the following.\n" +
                "$$1 Add target red SIGNI underneath a SIGNI on your field with a &R to your hand.\n" +
                "$$2 Target SIGNI on your field with a &R gets +3000 power and gains@>@U: Whenever this SIGNI attacks, you may pay %X %X. If you do, vanish target SIGNI on your opponent's field with power less than or equal to this SIGNI.@@until end of turn."
        );
        
        setName("en_fan", "Excavation of the Electric Horse");
        setDescription("en_fan",
                "@[@|Choose 1 of the following:|@]@\n" +
                "$$1 Target 1 red SIGNI under your red &R SIGNI, and add it to your hand.\n" +
                "$$2 Target 1 of your red &R SIGNI, and until end of turn, it gains:" +
                "@>@U: Whenever this SIGNI attacks, target 1 of your opponent's SIGNI with power equal to or less than this SIGNI's, and you may pay %X %X. If you do, banish it."
        );
        
		setName("zh_simplified", "电动马的发掘");
        setDescription("zh_simplified", 
                "从以下的2种选1种。\n" +
                "$$1 持有[升阶]的你的精灵的下面的红色的精灵1张作为对象，将其加入手牌。\n" +
                "$$2 持有[升阶]的你的精灵1只作为对象，直到回合结束时为止，其的力量+3000，其得到\n" +
                "@>@U :当这只精灵攻击时，自己的力量以下的对战对手的精灵1只作为对象，可以支付%X %X。这样做的场合，将其破坏。@@\n"
        );

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
            spell.setModeChoice(1);
        }
        
        private void onSpellEffPreTarget()
        {
            TargetFilter filterRise = new TargetFilter(TargetHint.ABILITY).own().SIGNI().withColor(CardColor.RED).withUseCondition(UseCondition.RISE);
            spell.setTargets(playerTargetCard(spell.getChosenModes() == 1 ?
                new TargetFilter(TargetHint.HAND).own().SIGNI().withColor(CardColor.RED).under(filterRise.getExportedData()) :
                filterRise
            ));
        }
        private void onSpellEff()
        {
            if(spell.getTarget() != null)
            {
                if(spell.getChosenModes() == 1)
                {
                    addToHand(spell.getTarget());
                } else {
                    AutoAbility attachedAuto = new AutoAbility(GameEventId.ATTACK, this::onAttachedAutoEff);
                    
                    attachAbility(spell.getTarget(), attachedAuto, ChronoDuration.turnEnd());
                }
            }
        }
        private void onAttachedAutoEff()
        {
            CardIndex target = getAbility().getSourceCardIndex().getIndexedInstance().playerTargetCard(new TargetFilter(TargetHint.BANISH).OP().SIGNI().withPower(0, getAbility().getSourceCardIndex().getIndexedInstance().getPower().getValue())).get();
            
            if(target != null && getAbility().getSourceCardIndex().getIndexedInstance().payEner(Cost.colorless(2)))
            {
                getAbility().getSourceCardIndex().getIndexedInstance().banish(target);
            }
        }
    }
}
