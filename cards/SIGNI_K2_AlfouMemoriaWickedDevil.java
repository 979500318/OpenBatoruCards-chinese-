package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.GameConst.GameEventId;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.Cost;
import open.batoru.data.ability.AutoAbility;
import open.batoru.data.ability.AbilityCondition.ConditionState;
import open.batoru.data.ability.events.EventRefresh;

public final class SIGNI_K2_AlfouMemoriaWickedDevil extends Card {
    
    public SIGNI_K2_AlfouMemoriaWickedDevil()
    {
        setImageSets("WXDi-P07-092", "WXDi-P07-092P");
        
        setOriginalName("凶魔　アルフォウ//メモリア");
        setAltNames("キョウマアルフォウメモリア Kyouma Arufou Memoria");
        setDescription("jp",
                "@U：このシグニがアタックしたとき、各プレイヤーは自分のデッキの上からカードを２枚トラッシュに置く。\n" +
                "@U：あなたがリフレッシュしたとき、対戦相手のシグニ１体を対象とし、%K %K %Kを支払ってもよい。そうした場合、それをバニッシュする。"
        );
        
        setName("en", "Alfou//Memoria, Doomed Evil");
        setDescription("en",
                "@U: Whenever this SIGNI attacks, each player puts the top two cards of their deck into their trash.\n" +
                "@U: Whenever you refresh your deck, you may pay %K %K %K. If you do, vanish target SIGNI on your opponent's field."
        );
        
        setName("en_fan", "Alfou//Memoria, Wicked Devil");
        setDescription("en_fan",
                "@U: Whenever this SIGNI attacks, each player puts the top 2 cards of their deck into the trash.\n" +
                "@U: Whenever you refresh, target 1 of your opponent's SIGNI, and you may pay %K %K %K. If you do, banish it."
        );
        
		setName("zh_simplified", "凶魔 阿尔芙//回忆");
        setDescription("zh_simplified", 
                "@U :当这只精灵攻击时，各玩家从自己的牌组上面把2张牌放置到废弃区。\n" +
                "@U :当你重构时，对战对手的精灵1只作为对象，可以支付%K %K %K。这样做的场合，将其破坏。\n"
        );

        setType(CardType.SIGNI);
        setColor(CardColor.BLACK);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.DEVIL);
        setLevel(2);
        setPower(5000);
        
        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA, PlayFormat.ENGLISH, PlayFormat.CHINESE);
    }
    
    @Override
    public IndexedInstance newIndexedInstance(int cardId) { return new IndexedInstance(cardId); }
    public class IndexedInstance extends Card.IndexedInstance
    {
        public IndexedInstance(int cardId)
        {
            super(cardId);
            
            registerAutoAbility(GameEventId.ATTACK, this::onAutoEff1);
            
            AutoAbility auto2 = registerAutoAbility(GameEventId.REFRESH, this::onAutoEff2);
            auto2.setCondition(this::onAutoEff2Cond);
        }
        
        private void onAutoEff1()
        {
            millDeck(2);
            millDeck(getOpponent(), 2);
        }
        
        private ConditionState onAutoEff2Cond()
        {
            return ((EventRefresh)getEvent()).getPlayer() == getCurrentOwner() ? ConditionState.OK : ConditionState.BAD;
        }
        private void onAutoEff2(CardIndex caller)
        {
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.BANISH).OP().SIGNI()).get();
            
            if(target != null && payEner(Cost.color(CardColor.BLACK, 3)))
            {
                banish(target);
            }
        }
    }
}
