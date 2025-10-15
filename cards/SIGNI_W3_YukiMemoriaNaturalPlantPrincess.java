package open.batoru.data.cards;

import open.batoru.core.Deck.DeckPosition;
import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.ChronoDuration;
import open.batoru.core.gameplay.GameConst.CardUnderType;
import open.batoru.core.gameplay.GameConst.GameEventId;
import open.batoru.core.gameplay.GameConst.GamePhase;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.ability.AbilityCondition.ConditionState;
import open.batoru.data.ability.AbilityConst.ActionHint;
import open.batoru.data.ability.AutoAbility;
import open.batoru.game.FieldZone;

public final class SIGNI_W3_YukiMemoriaNaturalPlantPrincess extends Card {
    
    public SIGNI_W3_YukiMemoriaNaturalPlantPrincess()
    {
        setImageSets("WXDi-P10-034", "WXDi-P10-034P");
        
        setOriginalName("羅植姫　ユキ//メモリア");
        setAltNames("ラショクヒメユキメモリア Rashokuhime Yuki Memoria");
        setDescription("jp",
                "@U：あなたのアタックフェイズ開始時、あなたのデッキの上からカードを４枚見る。その中から１枚を裏向きでシグニゾーンに置き、残りを好きな順番でデッキの一番下に置く。次のあなたのメインフェイズ開始時、そのカードを表向きにしてもよい。そうした場合、そのシグニが場にあるかぎり、そのシグニのパワーを＋5000する。そうしなかった場合、そのカードを手札に加える。" +
                "~#：対戦相手のアップ状態のシグニ１体を対象とし、それをトラッシュに置く。"
        );
        
        setName("en", "Yuki//Memoria, Natural Plant Queen");
        setDescription("en",
                "@U: At the beginning of your attack phase, look at the top four cards of your deck. Put a card from among them into your SIGNI Zone face down, and put the rest on the bottom of your deck in any order. At the beginning of your next main phase, you may turn that card face up. If you do, that SIGNI gets +5000 power as long as it remains on the field. If you don't, add that card to your hand." +
                "~#Put target upped SIGNI on your opponent's field into its owner's trash."
        );
        
        setName("en_fan", "Yuki//Memoria, Natural Plant Princess");
        setDescription("en_fan",
                "@U: At the beginning of your attack phase, look at the top 4 cards of your deck. Put 1 of them onto a SIGNI zone face down, and put the rest on the bottom of your deck in any order. At the beginning of your next main phase, you may turn that card face up. If you do, as long as that SIGNI is on the field, it gets +5000 power. If you don't, add that card to your hand." +
                "~#Target 1 of your opponent's upped SIGNI, and put it into the trash."
        );
        
		setName("zh_simplified", "罗植姬 雪//回忆");
        setDescription("zh_simplified", 
                "@U :你的攻击阶段开始时，从你的牌组上面看4张牌。从中把1张里向放置到精灵区，剩下的任意顺序放置到牌组最下面。下一个你的主要阶段开始时，可以把那张牌表向。这样做的场合，那只精灵在场上时，那只精灵的力量+5000。不这样做的场合，那张牌加入手牌。" +
                "~#对战对手的竖直状态的精灵1只作为对象，将其放置到废弃区。\n"
        );

        setCardFlags(CardFlag.LIFEBURST);
        
        setType(CardType.SIGNI);
        setColor(CardColor.WHITE);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.PLANT);
        setLevel(3);
        setPower(12000);
        
        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA, PlayFormat.ENGLISH, PlayFormat.CHINESE);
    }
    
    @Override
    public IndexedInstance newIndexedInstance(int cardId) { return new IndexedInstance(cardId); }
    public class IndexedInstance extends Card.IndexedInstance
    {
        public IndexedInstance(int cardId)
        {
            super(cardId);
            
            AutoAbility auto = registerAutoAbility(GameEventId.PHASE_START, this::onAutoEff);
            auto.setCondition(this::onAutoEffCond);
            
            registerLifeBurstAbility(this::onLifeBurstEff);
        }
        
        private ConditionState onAutoEffCond()
        {
            return isOwnTurn() && getCurrentPhase() == GamePhase.ATTACK_PRE ? ConditionState.OK : ConditionState.BAD;
        }
        private void onAutoEff(CardIndex caller)
        {
            look(4);
            
            CardIndex cardIndex = playerTargetCard(new TargetFilter(TargetHint.ZONE).own().fromLooked()).get();
            if(cardIndex != null)
            {
                FieldZone fieldZone = playerTargetZone(new TargetFilter(TargetHint.ZONE).own().SIGNI()).get();
                if(putOnZone(cardIndex, fieldZone.getZoneLocation(), CardUnderType.ZONE_GENERIC))
                {
                    callDelayedEffect(ChronoDuration.nextPhase(getOwner(), GamePhase.MAIN), () -> {
                        if(cardIndex.getLocation() != fieldZone.getZoneLocation()) return;
                        
                        if(playerChoiceAction(ActionHint.FACE_UP, ActionHint.HAND) == 1)
                        {
                            if(flip(cardIndex, CardFace.FRONT, true) && CardType.isSIGNI(cardIndex.getIndexedInstance().getTypeByRef()))
                            {
                                gainPower(cardIndex, 5000, ChronoDuration.permanent());
                            }
                        } else {
                            addToHand(cardIndex);
                        }
                    });
                }
            }
            
            while(getLookedCount() > 0)
            {
                CardIndex cardIndexToBottom = playerTargetCard(new TargetFilter(TargetHint.BOTTOM).own().fromLooked()).get();
                returnToDeck(cardIndexToBottom, DeckPosition.BOTTOM);
            }
        }
        
        private void onLifeBurstEff()
        {
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.TRASH).OP().SIGNI().upped()).get();
            trash(target);
        }
    }
}
