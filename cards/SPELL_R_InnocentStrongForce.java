package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.ChronoDuration;
import open.batoru.core.gameplay.GameConst.CardUnderCategory;
import open.batoru.core.gameplay.GameConst.CardUnderType;
import open.batoru.core.gameplay.GameConst.GameEventId;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.CardColor;
import open.batoru.data.CardConst.CardFlag;
import open.batoru.data.CardConst.CardType;
import open.batoru.data.CardConst.PlayFormat;
import open.batoru.data.Cost;
import open.batoru.data.ability.Ability.AbilityFlag;
import open.batoru.data.ability.AutoAbility;
import open.batoru.data.ability.AbilityCondition.ConditionState;
import open.batoru.data.ability.SpellAbility;
import open.batoru.data.ability.events.EventAttach;

public final class SPELL_R_InnocentStrongForce extends Card {

    public SPELL_R_InnocentStrongForce()
    {
        setImageSets("WXDi-P11-063");
        setLinkedImageSets("WXDi-P11-042","WXDi-P11-080","WXDi-P11-077");

        setOriginalName("無心の豪圧");
        setAltNames("ムシンノゴウアツ Mashin no Gouatsu");
        setDescription("jp",
                "対戦相手のパワー12000以下のシグニ１体を対象とし、それをバニッシュする。このスペルをチェックゾーンからあなたの《幻怪姫　エクス//メモリア》か《中装　デウス//メモリア》か《コードライド　マキナ//メモリア》１体の下に置いてもよい。\n\n" +
                "@U：このカードがシグニの下に置かれたとき、ターン終了時まで、このカードの上にあるシグニのパワーを＋2000する。" +
                "~#：対戦相手のアップ状態のシグニ１体を対象とし、それをバニッシュする。"
        );

        setName("en", "Innocent Force");
        setDescription("en",
                "Vanish target SIGNI on your opponent's field with power 12000 or less. You may put this spell from your Check Zone under an \"Ex//Memoria, Phantom Spirit Queen\", \"Deus//Memoria, High Armed\", or \"Machina//Memoria, Code Ride\" on your field.\n\n" +
                "@U: When this card is put underneath a SIGNI, the SIGNI on top of this card gets +2000 power until end of turn." +
                "~#Vanish target upped SIGNI on your opponent's field."
        );
        
        setName("en_fan", "Innocent Strong Force");
        setDescription("en_fan",
                "Target 1 of your opponent's SIGNI with power 12000 or less, and banish it. You may put this spell from your check zone under 1 of your \"Deus//Memoria, Medium Equipment\", \"Ex//Memoria, Phantom Apparition Princess\", or \"Code Ride Machina//Memoria\".\n\n" +
                "@U: When this card is put under a SIGNI, until end of turn, the SIGNI on top of this card gets +2000 power." +
                "~#Target 1 of your opponent's upped SIGNI, and banish it."
        );

		setName("zh_simplified", "无心的豪压");
        setDescription("zh_simplified", 
                "对战对手的力量12000以下的精灵1只作为对象，将其破坏。可以把这张魔法从检查区放置到你的《幻怪姫　エクス//メモリア》或《中装　デウス//メモリア》或《コードライド　マキナ//メモリア》1只的下面。\n" +
                "@U :当这张牌放置到精灵的下面时，直到回合结束时为止，这张牌的上面的精灵的力量+2000。" +
                "~#对战对手的竖直状态的精灵1只作为对象，将其破坏。\n"
        );

        setCardFlags(CardFlag.LIFEBURST);

        setType(CardType.SPELL);
        setColor(CardColor.RED);
        setCost(Cost.color(CardColor.RED, 1) + Cost.colorless(1));

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

            AutoAbility auto = registerAutoAbility(GameEventId.ATTACH, this::onAutoEff);
            auto.setCondition(this::onAutoEffCond);
            auto.getFlags().addValue(AbilityFlag.IGNORE_LOCATION | AbilityFlag.IGNORE_UNDER_FLAGS);
            
            registerLifeBurstAbility(this::onLifeBurstEff);
        }

        private void onSpellEffPreTarget()
        {
            spell.setTargets(playerTargetCard(new TargetFilter(TargetHint.BANISH).OP().SIGNI().withPower(0,12000)));
        }
        private void onSpellEff()
        {
            banish(spell.getTarget());
            
            CardIndex target = playerTargetCard(0,1, new TargetFilter().own().SIGNI().withName("幻怪姫　エクス//メモリア", "中装　デウス//メモリア", "コードライド　マキナ//メモリア")).get();
            attach(target, getCardIndex(), CardUnderType.UNDER_GENERIC);
        }
        
        private ConditionState onAutoEffCond(CardIndex caller)
        {
            return getEvent().getSourceCardIndex() == getCardIndex() && 
                   CardType.isSIGNI(caller.getCardReference().getType()) && EventAttach.getDataAttachUnderType().getUnderCategory() == CardUnderCategory.UNDER ? ConditionState.OK : ConditionState.BAD;
        }
        private void onAutoEff(CardIndex caller)
        {
            gainPower(getTopOverSIGNI(), 2000, ChronoDuration.turnEnd());
        }
        
        private void onLifeBurstEff()
        {
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.BANISH).OP().SIGNI().upped()).get();
            banish(target);
        }
    }
}
