package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.ChronoDuration;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.CardColor;
import open.batoru.data.CardConst.CardType;
import open.batoru.data.CardConst.PlayFormat;
import open.batoru.data.CardConst.UseTiming;
import open.batoru.data.Cost;
import open.batoru.data.ability.Ability;
import open.batoru.data.ability.AbilityConst.AbilityGain;
import open.batoru.data.ability.ActionAbility;
import open.batoru.data.ability.ConstantAbilityShared;
import open.batoru.data.ability.cost.ExceedCost;
import open.batoru.data.ability.modifiers.AbilityGainModifier;

public final class PIECE_W_InnocentOnePiece extends Card {
    
    public PIECE_W_InnocentOnePiece()
    {
        setImageSets("WXDi-P06-004");
        
        setOriginalName("イノセント・ワンピース");
        setAltNames("イノセントワンピース Inosento Wan Piisu");
        setDescription("jp",
                "このゲームの間、あなたのセンタールリグは以下の能力を得る。" +
                "@>@A -M -A @[エクシード４]@：対戦相手のシグニ１体を対象とし、ターン終了時まで、それは能力を失う。"
        );
        
        setName("en", "Innocent One - Piece");
        setDescription("en",
                "Your Center LRIG gains the following ability for the duration of the game. \n" +
                "@>@A -M -A @[Exceed 4]@: Target SIGNI on your opponent's field loses its abilities until end of turn."
        );
        
        setName("en_fan", "Innocent One Piece");
        setDescription("en_fan",
                "This game, your center LRIG gains:" +
                "@>@A -M -A @[Exceed 4]@: Target 1 of your opponent's SIGNI, and until end of turn, it loses its abilities."
        );
        
		setName("zh_simplified", "纯真·合唱");
        setDescription("zh_simplified", 
                "这场游戏期间，你的核心分身得到以下的能力。（成长后的新的核心分身依然得到能力）\n" +
                "@>@A 主要阶段:攻击阶段@[超越 4]@（从你的分身的下面把牌合计4张放置到分身废弃区）对战对手的精灵1只作为对象，直到回合结束时为止，其的能力失去。@@\n"
        );

        setType(CardType.PIECE);
        setColor(CardColor.WHITE);
        setCost(Cost.color(CardColor.WHITE, 1));
        setUseTiming(UseTiming.MAIN);
        
        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA, PlayFormat.ENGLISH, PlayFormat.CHINESE);
    }
    
    @Override
    public IndexedInstance newIndexedInstance(int cardId) { return new IndexedInstance(cardId); }
    public class IndexedInstance extends Card.IndexedInstance
    {
        public IndexedInstance(int cardId)
        {
            super(cardId);
            
            registerPieceAbility(this::onPieceEff);
        }
        
        private void onPieceEff()
        {
            ConstantAbilityShared attachedConst = new ConstantAbilityShared(new TargetFilter().own().LRIG(), new AbilityGainModifier(this::onAttachedConstEffModGetSample));
            attachPlayerAbility(getOwner(), attachedConst, ChronoDuration.permanent());
        }
        private Ability onAttachedConstEffModGetSample(CardIndex cardIndex)
        {
            ActionAbility attachedAct = cardIndex.getIndexedInstance().registerActionAbility(new ExceedCost(4), this::onAttachedActionEff);
            attachedAct.setActiveUseTiming(UseTiming.MAIN | UseTiming.ATTACK);
            
            return attachedAct;
        }
        private void onAttachedActionEff()
        {
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.MUTE).OP().SIGNI()).get();
            getAbility().getSourceCardIndex().getIndexedInstance().disableAllAbilities(target, AbilityGain.ALLOW, ChronoDuration.turnEnd());
        }
    }
}
