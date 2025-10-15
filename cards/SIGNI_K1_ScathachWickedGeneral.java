package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.ChronoDuration;
import open.batoru.core.gameplay.GameConst.GameEventId;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;

public final class SIGNI_K1_ScathachWickedGeneral extends Card {
    
    public SIGNI_K1_ScathachWickedGeneral()
    {
        setImageSets("WXDi-P01-079");
        
        setOriginalName("凶将　スカーアハ");
        setAltNames("キョウショウスカーアハ Kyoushou Sukaaaha");
        setDescription("jp",
                "@U：このシグニがアタックしたとき、あなたのトラッシュにカードが２０枚以上ある場合、対戦相手のシグニ１体を対象とし、ターン終了時まで、それのパワーを－3000する。"
        );
        
        setName("en", "Sgathaich, Doomed General");
        setDescription("en",
                "@U: Whenever this SIGNI attacks, if you have twenty or more cards in your trash, target SIGNI on your opponent's field gets --3000 power until end of turn."
        );
        
        setName("en_fan", "Scathach, Wicked General");
        setDescription("en_fan",
                "@U: Whenever this SIGNI attacks, if there are 20 or more cards in your trash, target 1 of your opponent's SIGNI, and until end of turn, it gets --3000 power."
        );
        
		setName("zh_simplified", "凶将 斯卡哈");
        setDescription("zh_simplified", 
                "@U :当这只精灵攻击时，你的废弃区的牌在20张以上的场合，对战对手的精灵1只作为对象，直到回合结束时为止，其的力量-3000。\n"
        );

        setType(CardType.SIGNI);
        setColor(CardColor.BLACK);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.VALOR);
        setLevel(1);
        setPower(2000);
        
        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA, PlayFormat.ENGLISH);
    }
    
    @Override
    public IndexedInstance newIndexedInstance(int cardId) { return new IndexedInstance(cardId); }
    public class IndexedInstance extends Card.IndexedInstance
    {
        public IndexedInstance(int cardId)
        {
            super(cardId);
            
            registerAutoAbility(GameEventId.ATTACK, this::onAutoEff);
        }
        
        private void onAutoEff()
        {
            if(getTrashCount(getOwner()) >= 20)
            {
                CardIndex target = playerTargetCard(new TargetFilter(TargetHint.MINUS).OP().SIGNI()).get();
                gainPower(target, -3000, ChronoDuration.turnEnd());
            }
        }
    }
}
