package open.batoru.data.cards;

import open.batoru.core.Deck.DeckPosition;
import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.GameConst.GameEventId;
import open.batoru.core.gameplay.GameConst.GamePhase;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.Cost;
import open.batoru.data.ability.AbilityConst.ActionHint;
import open.batoru.data.ability.AutoAbility;
import open.batoru.data.ability.AbilityCondition.ConditionState;

public final class SIGNI_K3_Code2434NuiSociere extends Card {
    
    public SIGNI_K3_Code2434NuiSociere()
    {
        setImageSets("WXDi-P00-042");
        
        setOriginalName("コード２４３４　ニュイ・ソシエール");
        setAltNames("コードニジサンジニュイソシエール Koodo Nijisanji Nyui Soshieeru");
        setDescription("jp",
                "@U：あなたの他の＜バーチャル＞のシグニ１体が場に出たとき、あなたのデッキの一番上を見る。それをトラッシュに置いてもよい。\n" +
                "@U：あなたのアタックフェイズ開始時、あなたのトラッシュに＜バーチャル＞のシグニが２０枚以上ある場合、対戦相手のシグニ１体を対象とし、%K %Xを支払ってもよい。そうした場合、それをバニッシュする。"
        );
        
        setName("en", "Nui Sociere, Code 2434");
        setDescription("en",
                "@U: Whenever another <<Virtual>> SIGNI enters your field, look at the top card of your deck. You may put it into the trash.\n" +
                "@U: At the beginning of your attack phase, if there are twenty or more <<Virtual>> SIGNI in your trash, you may pay %K %X. If you do, vanish target SIGNI on your opponent's field."
        );
        
        setName("en_fan", "Code 2434 Nui Sociere");
        setDescription("en_fan",
                "@U: Whenever 1 of your other <<Virtual>> SIGNI enters the field, look at the top card of your deck. You may put it into the trash.\n" +
                "@U: At the beginning of your attack phase, if there are 20 or more <<Virtual>> SIGNI in your trash, target 1 of your opponent's SIGNI, and you may pay %K %X. If you do, banish it."
        );
        
		setName("zh_simplified", "2434代号 纽伊·索西艾瑞");
        setDescription("zh_simplified", 
                "@U :当你的其他的<<バーチャル>>精灵1只出场时，看你的牌组最上面。可以将其放置到废弃区。\n" +
                "@U :你的攻击阶段开始时，你的废弃区的<<バーチャル>>精灵在20张以上的场合，对战对手的精灵1只作为对象，可以支付%K%X。这样做的场合，将其破坏。\n"
        );

        setType(CardType.SIGNI);
        setColor(CardColor.BLACK);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.VIRTUAL);
        setLevel(3);
        setPower(12000);
        
        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA, PlayFormat.ENGLISH);
    }
    
    @Override
    public IndexedInstance newIndexedInstance(int cardId) { return new IndexedInstance(cardId); }
    public class IndexedInstance extends Card.IndexedInstance
    {
        public IndexedInstance(int cardId)
        {
            super(cardId);
            
            AutoAbility auto1 = registerAutoAbility(GameEventId.ENTER, this::onAutoEff1);
            auto1.setCondition(this::onAutoEff1Cond);
            
            AutoAbility auto2 = registerAutoAbility(GameEventId.PHASE_START, this::onAutoEff2);
            auto2.setCondition(this::onAutoEff2Cond);
        }
        
        private ConditionState onAutoEff1Cond(CardIndex caller)
        {
            return caller != getCardIndex() && isOwnCard(caller) &&
                   caller.getIndexedInstance().getSIGNIClass().matches(CardSIGNIClass.VIRTUAL) ? ConditionState.OK : ConditionState.BAD;
        }
        private void onAutoEff1(CardIndex caller)
        {
            CardIndex cardIndex = look();
            
            if(cardIndex == null || playerChoiceAction(ActionHint.TRASH, ActionHint.TOP) != 1 || !trash(cardIndex))
            {
                returnToDeck(cardIndex, DeckPosition.TOP);
            }
        }
        
        private ConditionState onAutoEff2Cond()
        {
            return isOwnTurn() && getCurrentPhase() == GamePhase.ATTACK_PRE ? ConditionState.OK : ConditionState.BAD;
        }
        private void onAutoEff2(CardIndex caller)
        {
            if(new TargetFilter().own().SIGNI().fromTrash().withClass(CardSIGNIClass.VIRTUAL).getValidTargetsCount() >= 20)
            {
                CardIndex target = playerTargetCard(new TargetFilter(TargetHint.BANISH).OP().SIGNI()).get();
                
                if(target != null && payEner(Cost.color(CardColor.BLACK, 1) + Cost.colorless(1)))
                {
                    banish(target);
                }
            }
        }
    }
}
