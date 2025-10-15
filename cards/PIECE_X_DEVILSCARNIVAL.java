package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.ChronoDuration;
import open.batoru.data.DataTable;
import open.batoru.core.gameplay.GameConst.GameEventId;
import open.batoru.core.gameplay.GameConst.GamePhase;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.ability.AutoAbility;
import open.batoru.data.ability.AbilityCondition.ConditionState;
import open.batoru.data.ability.PieceAbility;

public final class PIECE_X_DEVILSCARNIVAL extends Card {
    
    public PIECE_X_DEVILSCARNIVAL()
    {
        setImageSets("WXDi-P04-006");
        
        setOriginalName("DEVIL'S CARNIVAL");
        setAltNames("デビルズカーニバル Debiruzu Kaanibaru");
        setDescription("jp",
                "あなたの＜悪魔＞のシグニ２体を場からトラッシュに置く。そうした場合、このゲームの間、あなたは以下の能力を得る。" +
                "@>@U：あなたのターン終了時、あなたのトラッシュから＜悪魔＞のシグニ１体を対象とし、それを手札に加える。"
        );
        
        setName("en", "DEVIL'S CARNIVAL");
        setDescription("en",
                "Put two <<Demon>> SIGNI on your field into their owner's trash. If you do, you gain the following ability for the duration of the game.\n" +
                "@>@U: At the end of your turn, add target <<Demon>> SIGNI from your trash to your hand."
        );
        
        setName("en_fan", "DEVIL'S CARNIVAL");
        setDescription("en_fan",
                "Put 2 of your <<Devil>> SIGNI from the field into the trash. If you do, during this game, you gain the following ability:" +
                "@>@U: At the end of your turn, target 1 <<Devil>> SIGNI from your trash, and add it to your hand."
        );
        
		setName("zh_simplified", "DEVIL'S CARNIVAL");
        setDescription("zh_simplified", 
                "你的<<悪魔>>精灵2只从场上放置到废弃区。这样做的场合，这场游戏期间，你得到以下的能力。\n" +
                "@>@U 你的回合结束时，从你的废弃区把<<悪魔>>精灵1张作为对象，将其加入手牌。@@\n"
        );

        setType(CardType.PIECE);
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
            
            PieceAbility piece = registerPieceAbility(this::onPieceEff);
            piece.setCondition(this::onPieceEffCond);
        }
        
        private ConditionState onPieceEffCond()
        {
            return new TargetFilter().own().SIGNI().withClass(CardSIGNIClass.DEVIL).getValidTargetsCount() >= 2 ? ConditionState.OK : ConditionState.WARN;
        }
        private void onPieceEff()
        {
            DataTable<CardIndex> data = playerTargetCard(2, new TargetFilter(TargetHint.TRASH).own().SIGNI().withClass(CardSIGNIClass.DEVIL));
            
            if(data.get() != null && trash(data) == 2)
            {
                AutoAbility attachedAuto = new AutoAbility(GameEventId.PHASE_START, this::onAttachedEff);
                attachedAuto.setCondition(this::onAttachedAutoEffCond);
                
                attachPlayerAbility(getOwner(), attachedAuto, ChronoDuration.permanent());
            }
        }
        private ConditionState onAttachedAutoEffCond(CardIndex caller)
        {
            return isOwnTurn() && getCurrentPhase() == GamePhase.END ? ConditionState.OK : ConditionState.BAD;
        }
        private void onAttachedEff(CardIndex caller)
        {
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.HAND).own().SIGNI().withClass(CardSIGNIClass.DEVIL).fromTrash()).get();
            addToHand(target);
        }
    }
}
