package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.GameConst.CardStateFlag;
import open.batoru.core.gameplay.GameConst.GameEventId;
import open.batoru.core.gameplay.GameConst.GamePhase;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.ability.AutoAbility;
import open.batoru.data.ability.AbilityCondition.ConditionState;
import open.batoru.data.ability.stock.StockAbilityHarmony;

public final class SIGNI_X3_UrithDissonaLucentDevilPrincess extends Card {

    public SIGNI_X3_UrithDissonaLucentDevilPrincess()
    {
        setImageSets("WXDi-P12-055", "WXDi-P12-055P");

        setOriginalName("透魔姫　ウリス//ディソナ");
        setAltNames("トウマキウリスディソナ Toumaki Urisu Disona");
        setDescription("jp",
                "=H ルリグ２体\n\n" +
                "@U：あなたのアタックフェイズ開始時、このシグニがアップ状態の場合、対戦相手のシグニ１体を対象とし、あなたの手札を１枚選んでもよい。そうした場合、対戦相手は%Wi%Ri%Bi%Gi%Ki%Xiから１つを宣言する。あなたはその選んだカードを捨て、そのカードが宣言されたアイコンを持たない場合、それをバニッシュする。"
        );

        setName("en", "Urith//Dissona, Lucent Evil Queen");
        setDescription("en",
                "=H Two LRIG\n\n" +
                "@U: At the beginning of your attack phase, if this SIGNI is upped, you may choose a card from your hand. If you do, your opponent declares a %Wi, %Ri, %Bi, %Gi, %Ki, or %Xi. Discard your chosen card. If it is a card without the declared icon, vanish target SIGNI on your opponent's field."
        );
        
        setName("en_fan", "Urith//Dissona, Lucent Devil Princess");
        setDescription("en_fan",
                "=H 2 LRIGs\n\n" +
                "@U: At the beginning of your attack phase, if this SIGNI is upped, target 1 of your opponent's SIGNI, and you may choose 1 card in your hand. If you do, your opponent declares 1 from either%Wi%Ri%Bi%Gi%Ki%Xi. Discard the chosen card, and if it doesn't match the declared icon, banish that SIGNI."
        );

		setName("zh_simplified", "透魔姬 乌莉丝//失调");
        setDescription("zh_simplified", 
                "=H分身2只（当这只精灵出场时，如果不把你的竖直状态的分身2只横置，那么将此牌横置）\n" +
                "@U 你的攻击阶段开始时，这只精灵在竖直状态的场合，对战对手的精灵1只作为对象，可以选你的手牌1张。这样做的场合，对战对手从%Wi%Ri%Bi%Gi%Ki%Xi中把1种宣言。你把那张选的牌舍弃，那张牌不持有宣言的图标的场合，将其破坏。\n"
        );

        setCardFlags(CardFlag.DISSONA);

        setType(CardType.SIGNI);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.DEVIL);
        setLevel(3);
        setPower(10000);

        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA, PlayFormat.ENGLISH, PlayFormat.CHINESE);
    }

    @Override
    public IndexedInstance newIndexedInstance(int cardId) { return new IndexedInstance(cardId); }
    public class IndexedInstance extends Card.IndexedInstance
    {
        public IndexedInstance(int cardId)
        {
            super(cardId);

            registerStockAbility(new StockAbilityHarmony(2, new TargetFilter()));

            AutoAbility auto = registerAutoAbility(GameEventId.PHASE_START, this::onAutoEff);
            auto.setCondition(this::onAutoEffCond);
        }

        private ConditionState onAutoEffCond()
        {
            return isOwnTurn() && getCurrentPhase() == GamePhase.ATTACK_PRE ? ConditionState.OK : ConditionState.BAD;
        }
        private void onAutoEff(CardIndex caller)
        {
            if(!isState(CardStateFlag.DOWNED))
            {
                CardIndex target = playerTargetCard(new TargetFilter(TargetHint.BANISH).OP().SIGNI()).get();
                
                if(target != null)
                {
                    CardIndex cardIndex = playerTargetCard(0,1, new TargetFilter().own().fromHand()).get();
                    
                    if(look(cardIndex))
                    {
                        CardColor color = playerChoiceColor(getOpponent(), CardColor.values());
                        
                        if(discard(cardIndex).get() != null &&
                           (cardIndex.getIndexedInstance().getColor().getPrimaryValue() == CardColor.COLORLESS && color != CardColor.COLORLESS) ||
                            !cardIndex.getIndexedInstance().getColor().matches(color))
                        {
                            banish(target);
                        }
                    }
                }
            }
        }
    }
}
