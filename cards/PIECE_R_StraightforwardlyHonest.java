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
import open.batoru.data.ability.ActionAbility;
import open.batoru.data.ability.ConstantAbilityShared;
import open.batoru.data.ability.cost.ExceedCost;
import open.batoru.data.ability.modifiers.AbilityGainModifier;

public final class PIECE_R_StraightforwardlyHonest extends Card {
    
    public PIECE_R_StraightforwardlyHonest()
    {
        setImageSets("WXDi-P07-003");
        
        setOriginalName("まっすぐオーネスト");
        setAltNames("マッスグオーネスト Massugu Oonesuto");
        setDescription("jp",
                "このゲームの間、あなたのセンタールリグは以下の能力を得る。" +
                "@>@A -M -A @[エクシード４]@：対戦相手のレベル１のシグニ１体を対象とし、それをバニッシュする。"
        );
        
        setName("en", "True Honesty");
        setDescription("en",
                "Your Center LRIG gains the following ability for the duration of the game. \n" +
                "@>@A -M -A @[Exceed 4]@: Vanish target level one SIGNI on your opponent's field."
        );
        
        setName("en_fan", "Straightforwardly Honest");
        setDescription("en_fan",
                "This game, your center LRIG gains:" +
                "@>@A -M -A @[Exceed 4]@: Target 1 of your opponent's level 1 SIGNI, and banish it."
        );
        
		setName("zh_simplified", "志在真诚");
        setDescription("zh_simplified", 
                "这场游戏期间，你的核心分身得到以下的能力。（成长后的新的核心分身依然得到能力）\n" +
                "@>@A 主要阶段:攻击阶段@[超越 4]@（从你的分身的下面把牌合计4张放置到分身废弃区）对战对手的等级1的精灵1只作为对象，将其破坏。@@\n"
        );

        setType(CardType.PIECE);
        setColor(CardColor.RED);
        setCost(Cost.color(CardColor.RED, 1));
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
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.BANISH).OP().SIGNI().withLevel(1)).get();
            getAbility().getSourceCardIndex().getIndexedInstance().banish(target);
        }
    }
}
