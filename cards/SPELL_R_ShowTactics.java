package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.CardColor;
import open.batoru.data.CardConst.CardFlag;
import open.batoru.data.CardConst.CardType;
import open.batoru.data.CardConst.PlayFormat;
import open.batoru.data.Cost;
import open.batoru.data.ability.AbilityCondition.ConditionState;
import open.batoru.data.ability.SpellAbility;

public final class SPELL_R_ShowTactics extends Card {

    public SPELL_R_ShowTactics()
    {
        setImageSets("WXDi-P13-068");

        setOriginalName("見得の戦術");
        setAltNames("ミエノセンジュツ Mie no Senjutsu");
        setDescription("jp",
                "あなたの場に#Sのシグニが３体ある場合、対戦相手のシグニ１体を対象とし、それをバニッシュする。" +
                "~#：対戦相手のアップ状態のシグニ１体を対象とし、それをバニッシュする。"
        );

        setName("en", "Flamboyant Tactics");
        setDescription("en",
                "If there are three #S SIGNI on your field, vanish target SIGNI on your opponent's field." +
                "~#Vanish target upped SIGNI on your opponent's field."
        );
        
        setName("en_fan", "Show Tactics");
        setDescription("en_fan",
                "If there are 3 #S @[Dissona]@ SIGNI on your field, target 1 of your opponent's SIGNI, and banish it." +
                "~#Target 1 of your opponent's upped SIGNI, and banish it."
        );

		setName("zh_simplified", "见得的战术");
        setDescription("zh_simplified", 
                "你的场上的#S的精灵在3只的场合，对战对手的精灵1只作为对象，将其破坏。" +
                "~#对战对手的竖直状态的精灵1只作为对象，将其破坏。\n"
        );

        setCardFlags(CardFlag.LIFEBURST | CardFlag.DISSONA);

        setType(CardType.SPELL);
        setColor(CardColor.RED);
        setCost(Cost.color(CardColor.RED, 1) + Cost.colorless(1));

        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA, PlayFormat.ENGLISH);
    }

    @Override
    public IndexedInstance newIndexedInstance(int cardId) { return new IndexedInstance(cardId); }
    public class IndexedInstance extends Card.IndexedInstance
    {
        public IndexedInstance(int cardId)
        {
            super(cardId);

            SpellAbility spell = registerSpellAbility(this::onSpellEff);
            spell.setCondition(this::onSpellEffCond);

            registerLifeBurstAbility(this::onLifeBurstEff);
        }
        
        private ConditionState onSpellEffCond()
        {
            return new TargetFilter().own().SIGNI().dissona().getValidTargetsCount() == 3 &&
                   new TargetFilter().OP().SIGNI().getValidTargetsCount() > 0 ? ConditionState.OK : ConditionState.WARN;
        }
        private void onSpellEff()
        {
            if(new TargetFilter().own().SIGNI().dissona().getValidTargetsCount() == 3)
            {
                CardIndex target = playerTargetCard(new TargetFilter(TargetHint.BANISH).OP().SIGNI()).get();
                banish(target);
            }
        }
        
        private void onLifeBurstEff()
        {
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.BANISH).OP().SIGNI().upped()).get();
            banish(target);
        }
    }
}
