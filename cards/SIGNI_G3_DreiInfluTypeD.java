package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.data.DataTable;
import open.batoru.core.gameplay.GameConst.CardLocation;
import open.batoru.core.gameplay.GameConst.GameEventId;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.Cost;
import open.batoru.data.ability.AbilityConst.UseLimit;
import open.batoru.data.ability.AutoAbility;
import open.batoru.data.ability.AbilityCondition.ConditionState;
import open.batoru.data.ability.events.EventTarget;
import open.batoru.data.ability.stock.StockAbilityHarmony;

public final class SIGNI_G3_DreiInfluTypeD extends Card {
    
    public SIGNI_G3_DreiInfluTypeD()
    {
        setImageSets("WXDi-P02-043");
        
        setOriginalName("ドライ＝インフルＤ型");
        setAltNames("ドライインフルディーガタ Dorai Infuru Dii Gata");
        setDescription("jp",
                "=H 青のルリグ１体\n\n" +
                "@U：このシグニがアタックしたとき、対戦相手のパワー10000以上のシグニを２体まで対象とし、%G %G %G %X %X %Xを支払ってもよい。そうした場合、それらをバニッシュする。\n" +
                "@U $T1：このシグニが対戦相手の、能力か効果の対象になったとき、カードを１枚引き[[エナチャージ１]]をする。"
        );
        
        setName("en", "Influen D Type: Drei");
        setDescription("en",
                "=H One blue LRIG\n\n" +
                "@U: Whenever this SIGNI attacks, you may pay %G %G %G %X %X %X. If you do, vanish up to two target SIGNI on your opponent's field with power 10000 or more.\n" +
                "@U $T1: When this SIGNI becomes the target of your opponent's ability or effect, draw a card and [[Ener Charge 1]]."
        );
        
        setName("en_fan", "Drei-Influ Type D");
        setDescription("en_fan",
                "=H 1 blue LRIG\n\n" +
                "@U: Whenever this SIGNI attacks, target up to 2 of your opponent's SIGNI with power 10000 or more, and you may pay %G %G %G %X %X %X. If you do, banish them.\n" +
                "@U $T1: When this SIGNI is targeted by your opponent's ability or effect, draw 1 card and [[Ener Charge 1]]."
        );
        
		setName("zh_simplified", "DREI=流感D型");
        setDescription("zh_simplified", 
                "=H蓝色的分身1只（当这只精灵出场时，如果不把你的竖直状态的蓝色的分身1只#D，那么将此牌#D）\n" +
                "@U :当这只精灵攻击时，对战对手的力量10000以上的精灵2只最多作为对象，可以支付%G %G %G%X %X %X。这样做的场合，将这些破坏。\n" +
                "@U $T1 :当这只精灵被作为对战对手的，能力或效果的对象时，抽1张牌并[[能量填充1]]。\n"
        );

        setType(CardType.SIGNI);
        setColor(CardColor.GREEN);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.VENOM_FANG);
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
            
            registerStockAbility(new StockAbilityHarmony(1, new TargetFilter().withColor(CardColor.BLUE)));
            
            registerAutoAbility(GameEventId.ATTACK, this::onAutoEff1);
            
            AutoAbility auto2 = registerAutoAbility(GameEventId.TARGET, this::onAutoEff2);
            auto2.setCondition(this::onAutoEff2Cond);
            auto2.setUseLimit(UseLimit.TURN, 1);
        }
        
        private void onAutoEff1()
        {
            DataTable<CardIndex> data = playerTargetCard(0,2, new TargetFilter(TargetHint.BANISH).OP().SIGNI().withPower(10000,0));
            
            if(data.get() != null && payEner(Cost.color(CardColor.GREEN, 3) + Cost.colorless(3)))
            {
                banish(data);
            }
        }
        
        private ConditionState onAutoEff2Cond()
        {
            return getEvent().getSourceAbility() != null && !isOwnCard(getEvent().getSourceCardIndex()) &&
                   CardLocation.isSIGNI(getCardIndex().getLocation()) &&
                   EventTarget.getDataSourceTargetRole() != getCurrentOwner() ? ConditionState.OK : ConditionState.BAD;
        }
        private void onAutoEff2()
        {
            draw(1);
            enerCharge(1);
        }
    }
}
