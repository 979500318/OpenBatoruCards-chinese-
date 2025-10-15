package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.GameConst.CardLocation;
import open.batoru.core.gameplay.GameConst.GameEventId;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.Cost;
import open.batoru.data.ability.AbilityConst.Enter;
import open.batoru.data.ability.cost.ExceedCost;

public final class SIGNI_R3_HiranaMemoriaCrimsonAngelPrincess extends Card {
    
    public SIGNI_R3_HiranaMemoriaCrimsonAngelPrincess()
    {
        setImageSets("WXDi-P08-040", "WXDi-P08-040P");
        
        setOriginalName("紅天姫　ヒラナ//メモリア");
        setAltNames("コウテンキヒラナメモリア Koutenki Hirana Memoria");
        setDescription("jp",
                "@U：このシグニがバニッシュされたとき、%X %Xを支払ってもよい。そうした場合、あなたの手札から白か青のシグニ１枚をダウン状態で場に出す。そのシグニの@E能力は発動しない。\n" +
                "@A @[エクシード４]@：あなたのエナゾーンとライフクロスにあるすべてのカードをトラッシュに置き、手札をすべて捨てる。カードを２枚引き【エナチャージ２】をする。" +
                "~#：どちらか１つを選ぶ。\n" +
                "$$1対戦相手のアップ状態のシグニ１体を対象とし、それをバニッシュする。\n" +
                "$$2カードを１枚引く。"
        );
        
        setName("en", "Hirana//Memoria, Crimson Angel Queen");
        setDescription("en",
                "@U: When this SIGNI is vanished, you may pay %X %X. If you do, put a white or blue SIGNI from your hand onto your field downed. The @E abilities of SIGNI put onto your field this way do not activate.\n" +
                "@A @[Exceed 4]@: Put all cards in your Ener Zone and Life Cloth into your trash and discard your hand. Draw two cards and [[Ener Charge 2]]." +
                "~#Choose one -- \n$$1 Vanish target upped SIGNI on your opponent's field. \n$$2 Draw a card."
        );
        
        setName("en_fan", "Hirana//Memoria, Crimson Angel Princess");
        setDescription("en_fan",
                "@U: When this SIGNI is banished, you may pay %X %X. If you do, put 1 white or blue SIGNI from your hand onto the field downed. Its @E abilities don't activate.\n" +
                "@A @[Exceed 4]@: Put all cards from your ener zone and life cloth into the trash, and discard all cards from your hand. Draw 2 cards and [[Ener Charge 2]]." +
                "~#@[@|Choose 1 of the following:|@]@\n" +
                "$$1 Target 1 of your opponent's upped SIGNI, and banish it.\n" +
                "$$2 Draw 1 card."
        );
        
		setName("zh_simplified", "红天姬 平和//回忆");
        setDescription("zh_simplified", 
                "@U :当这只精灵被破坏时，可以支付%X %X。这样做的场合，从你的手牌把白色或蓝色的精灵1张以#D状态出场。那只精灵的@E能力不能发动。\n" +
                "@A @[超越 4]@:你的能量区和生命护甲的全部的牌放置到废弃区，手牌全部舍弃。抽2张牌并[[能量填充2]]。" +
                "~#以下选1种。\n" +
                "$$1 对战对手的竖直状态的精灵1只作为对象，将其破坏。\n" +
                "$$2 抽1张牌。\n"
        );

        setCardFlags(CardFlag.LIFEBURST);
        
        setType(CardType.SIGNI);
        setColor(CardColor.RED);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.ANGEL);
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
            
            registerAutoAbility(GameEventId.BANISH, this::onAutoEff);
            
            registerActionAbility(new ExceedCost(4), this::onActionEff);
            
            registerLifeBurstAbility(this::onLifeBurstEff);
        }
        
        private void onAutoEff()
        {
            if(payEner(Cost.colorless(2)))
            {
                CardIndex cardIndex = playerTargetCard(new TargetFilter(TargetHint.FIELD).own().SIGNI().withColor(CardColor.WHITE, CardColor.BLUE).fromHand().playable()).get();
                putOnField(cardIndex, Enter.DONT_ACTIVATE | Enter.DOWNED);
            }
        }
        
        private void onActionEff()
        {
            trash(getCardsInEner(getOwner()));
            trash(CardLocation.LIFE_CLOTH, getLifeClothCount(getOwner()));
            discard(getCardsInHand(getOwner()));
            
            draw(2);
            enerCharge(2);
        }
        
        private void onLifeBurstEff()
        {
            if(playerChoiceMode() == 1)
            {
                CardIndex target = playerTargetCard(new TargetFilter(TargetHint.BANISH).OP().SIGNI().upped()).get();
                banish(target);
            } else {
                draw(1);
            }
        }
    }
}
