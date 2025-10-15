package open.batoru.data.cards;

import open.batoru.core.gameplay.ChronoDuration;
import open.batoru.data.Card;
import open.batoru.data.CardConst.CardType;
import open.batoru.data.CardConst.PlayFormat;
import open.batoru.data.CardConst.UseTiming;
import open.batoru.data.ability.AbilityConst.AbilityGain;

public final class PIECE_X_ResetMemoria extends Card {
    
    public PIECE_X_ResetMemoria()
    {
        setImageSets("WXDi-P08-006");
        
        setOriginalName("リセット・メモリア");
        setAltNames("リセットメモリア Risetto Memoria");
        setDescription("jp",
                "カードを１枚引く。ターン終了時まで、対戦相手のすべてのシグニは能力を失う。"
        );
        
        setName("en", "Reset Memoria");
        setDescription("en",
                "Draw a card. All SIGNI on your opponent's field lose their abilities until end of turn. "
        );
        
        setName("en_fan", "Reset Memoria");
        setDescription("en_fan",
                "Draw 1 card. Until end of turn, all of your opponent's SIGNI lose their abilities."
        );
        
		setName("zh_simplified", "重设·回忆");
        setDescription("zh_simplified", 
                "抽1张牌。直到回合结束时为止，对战对手的全部的精灵的能力失去。（在这张和音之后出场的精灵不受这个效果的影响）\n"
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
            
            registerPieceAbility(this::onPieceEff);
        }
        
        private void onPieceEff()
        {
            draw(1);

            disableAllAbilities(getSIGNIOnField(getOpponent()), AbilityGain.ALLOW, ChronoDuration.turnEnd());
        }
    }
}
