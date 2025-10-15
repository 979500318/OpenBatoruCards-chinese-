package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.ChronoDuration;
import open.batoru.core.gameplay.GameConst.GameEventId;
import open.batoru.core.gameplay.GameLog;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.CardColor;
import open.batoru.data.CardConst.CardType;
import open.batoru.data.CardConst.PlayFormat;
import open.batoru.data.CardConst.UseTiming;
import open.batoru.data.ability.AutoAbility;
import open.batoru.data.ability.AbilityCondition.ConditionState;
import open.batoru.data.ability.PieceAbility;

public final class PIECE_G_GGG extends Card {
    
    public PIECE_G_GGG()
    {
        setImageSets("WXDi-P03-002");
        
        setOriginalName("G-G-G");
        setAltNames("ジージージー Jii Jii Jii GGG");
        setDescription("jp",
                "=U あなたの場に緑のルリグがいる\n\n" +
                "このゲームの間、あなたは以下の能力を得る。" +
                "@>@U：あなたのターンの間、あなたのルリグがグロウしたとき、それがそのターンであなたの最初のグロウである場合、[[エナチャージ１]]をする。"
        );
        
        setName("en", "G - G - G");
        setDescription("en",
                "=U You have a green LRIG on your field.\n\n" +
                "You gain the following ability for the duration of the game.\n" +
                "@>@U: During your turn, when a LRIG on your field grows, if that was your first grow this turn, [[Ener Charge 1]]."
        );
        
        setName("en_fan", "G-G-G");
        setDescription("en_fan",
                "=U There is a green LRIG on your field\n\n" +
                "During this game, you gain the following ability:" +
                "@>@U: During your turn, whenever your LRIG grows, if it is your first grow this turn, [[Ener Charge 1]]."
        );
        
		setName("zh_simplified", "G-G-G");
        setDescription("zh_simplified", 
                "=U你的场上有绿色的分身\n" +
                "这场游戏期间，你得到以下的能力。\n" +
                "@>@U 你的回合期间，当你的分身成长时，其是那个回合你的最初的成长的场合，[[能量填充1]]。@@\n"
        );

        setType(CardType.PIECE);
        setColor(CardColor.GREEN);
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
            return new TargetFilter().own().anyLRIG().withColor(CardColor.GREEN).getValidTargetsCount() > 0 ? ConditionState.OK : ConditionState.BAD;
        }
        private void onPieceEff()
        {
            AutoAbility attachedAuto = new AutoAbility(GameEventId.GROW, this::onAttachedEff);
            attachedAuto.setCondition(this::onAttachedAutoEffCond);
            
            attachPlayerAbility(getOwner(), attachedAuto, ChronoDuration.permanent());
        }
        private ConditionState onAttachedAutoEffCond(CardIndex caller)
        {
            return isOwnTurn() && isOwnCard(caller) &&
                   GameLog.getTurnRecordsCount(event -> event.getId() == GameEventId.GROW && isOwnCard(event.getCaller())) == 1 ? ConditionState.OK : ConditionState.BAD;
        }
        private void onAttachedEff(CardIndex caller)
        {
            enerCharge(1);
        }
    }
}
