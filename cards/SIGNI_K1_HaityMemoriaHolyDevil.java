package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.GameConst.CardLocation;
import open.batoru.core.gameplay.GameConst.GameEventId;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.ability.AbilityConst.UseLimit;
import open.batoru.data.ability.AutoAbility;
import open.batoru.data.ability.AbilityCondition.ConditionState;
import open.batoru.data.ability.events.EventMove;

public final class SIGNI_K1_HaityMemoriaHolyDevil extends Card {
    
    public SIGNI_K1_HaityMemoriaHolyDevil()
    {
        setImageSets("WXDi-P07-052", "WXDi-P07-052P");
        
        setOriginalName("聖魔　ハイティ//メモリア");
        setAltNames("セイマハイティメモリア Seima Haiti Memoria");
        setDescription("jp",
                "@U $T1：あなたのライフクロス１枚がクラッシュされるかトラッシュに置かれたとき、カードを１枚引く。"
        );
        
        setName("en", "Haity//Memoria, Blessed Evil");
        setDescription("en",
                "@U $T1: When one of your Life Cloth is crushed or put into the trash, draw a card."
        );
        
        setName("en_fan", "Haity//Memoria, Holy Devil");
        setDescription("en_fan",
                "@U $T1: When 1 of your life cloth is crushed or put into the trash, draw 1 card."
        );
        
		setName("zh_simplified", "圣魔 海蒂//回忆");
        setDescription("zh_simplified", 
                "@U $T1 :当你的生命护甲1张被击溃或放置到废弃区时，抽1张牌。\n"
        );

        setType(CardType.SIGNI);
        setColor(CardColor.WHITE);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.DEVIL);
        setLevel(1);
        setPower(2000);
        
        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA, PlayFormat.ENGLISH, PlayFormat.CHINESE);
    }
    
    @Override
    public IndexedInstance newIndexedInstance(int cardId) { return new IndexedInstance(cardId); }
    public class IndexedInstance extends Card.IndexedInstance
    {
        public IndexedInstance(int cardId)
        {
            super(cardId);
            
            AutoAbility auto = registerAutoAbility(GameEventId.MOVE, this::onAutoEff);
            auto.setCondition(this::onAutoEffCond);
            auto.setUseLimit(UseLimit.TURN, 1);
        }
        
        private ConditionState onAutoEffCond(CardIndex caller)
        {
            return isOwnCard(caller) && caller.getOldLocation() == CardLocation.LIFE_CLOTH &&
                   (EventMove.getDataMoveLocation() == CardLocation.TRASH || EventMove.getDataMoveLocation() == CardLocation.CHECK_ZONE) ? ConditionState.OK : ConditionState.BAD;
        }
        private void onAutoEff(CardIndex caller)
        {
            draw(1);
        }
    }
}
