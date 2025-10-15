package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.ChronoDuration;
import open.batoru.core.gameplay.GameConst.CardUnderType;
import open.batoru.core.gameplay.GameConst.GameEventId;
import open.batoru.core.gameplay.GameConst.UseCondition;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.Cost;
import open.batoru.data.ability.AbilityCondition.ConditionState;
import open.batoru.data.ability.cost.EnerCost;
import open.batoru.data.ability.stock.StockAbilityShadow;

public final class SIGNI_R3_ZeusiasCrimsonAngelPrincess extends Card {
    
    public SIGNI_R3_ZeusiasCrimsonAngelPrincess()
    {
        setImageSets("WXDi-P03-038");
        
        setOriginalName("紅天姫　ゼウシアス");
        setAltNames("コウテンキゼウシアス Koutenki Zeushias");
        setDescription("jp",
                "=R あなたの＜天使＞のシグニ１体の上に置く\n\n" +
                "@U：このシグニがアタックしたとき、このシグニの下に白の＜天使＞がある場合、次の対戦相手のターン終了時まで、このシグニのパワーは＋3000されこのシグニは[[シャドウ（シグニ）]]を得る。青の＜天使＞がある場合、カードを２枚引く。緑の＜天使＞がある場合、[[エナチャージ２]]をする。\n" +
                "@E %X：あなたのトラッシュから＜天使＞のシグニ１枚を対象とし、それをこのシグニの下に置く。"
        );
        
        setName("en", "Zeusias, Crimson Angel Queen");
        setDescription("en",
                "=R Place on top of an <<Angel>> SIGNI on your field.\n" +
                "@U: Whenever this SIGNI attacks, if there is a white <<Angel>> underneath it, this SIGNI gets +3000 power and gains [[Shadow -- SIGNI]] until the end of your opponent's next end phase. If there is a blue <<Angel>>, draw two cards. If there is a green <<Angel>>, [[Ener Charge 2]].\n" +
                "@E %X: Put target <<Angel>> SIGNI from your trash under this SIGNI."
        );
        
        setName("en_fan", "Zeusias, Crimson Angel Princess");
        setDescription("en_fan",
                "=R Put on 1 of your <<Angel>> SIGNI\n\n" +
                "@U: Whenever this SIGNI attacks, if there is a white <<Angel>> under this SIGNI, until the end of your opponent's next turn, this SIGNI gets +3000 power, and [[Shadow (SIGNI)]]. If there is a blue <<Angel>>, draw 2 cards. If there is a green <<Angel>>, [[Ener Charge 2]].\n" +
                "@E %X: Target 1 <<Angel>> SIGNI from your trash, and put it under this SIGNI."
        );
        
		setName("zh_simplified", "红天姬 宙斯");
        setDescription("zh_simplified", 
                "=R在你的<<天使>>精灵1只的上面放置\n" +
                "@U :当这只精灵攻击时，这只精灵的下面有白色的<<天使>>的场合，直到下一个对战对手的回合结束时为止，这只精灵的力量+3000且这只精灵得到[[暗影（精灵）]]。蓝色的<<天使>>的场合，抽2张牌。绿色的<<天使>>的场合，[[能量填充2]]。\n" +
                "@E %X:从你的废弃区把<<天使>>精灵1张作为对象，将其放置到这只精灵的下面。\n"
        );

        setType(CardType.SIGNI);
        setColor(CardColor.RED);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.ANGEL);
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
            
            setUseCondition(UseCondition.RISE, 1, new TargetFilter().withClass(CardSIGNIClass.ANGEL));
            
            registerAutoAbility(GameEventId.ATTACK, this::onAutoEff);
            
            registerEnterAbility(new EnerCost(Cost.colorless(1)), this::onEnterEff);
        }
        
        private void onAutoEff()
        {
            if(new TargetFilter().own().under(getCardIndex()).withClass(CardSIGNIClass.ANGEL).withColor(CardColor.WHITE).getValidTargetsCount() > 0)
            {
                gainPower(getCardIndex(), 3000, ChronoDuration.nextTurnEnd(getOpponent()));
                attachAbility(getCardIndex(), new StockAbilityShadow(this::onAttachedStockEffAddCond), ChronoDuration.nextTurnEnd(getOpponent()));
            }
            
            if(new TargetFilter().own().under(getCardIndex()).withClass(CardSIGNIClass.ANGEL).withColor(CardColor.BLUE).getValidTargetsCount() > 0)
            {
                draw(2);
            }
            
            if(new TargetFilter().own().under(getCardIndex()).withClass(CardSIGNIClass.ANGEL).withColor(CardColor.GREEN).getValidTargetsCount() > 0)
            {
                enerCharge(2);
            }
        }
        private ConditionState onAttachedStockEffAddCond(CardIndex cardIndexSource)
        {
            return CardType.isSIGNI(cardIndexSource.getCardReference().getType()) ? ConditionState.OK : ConditionState.BAD;
        }
        
        private void onEnterEff()
        {
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.UNDER).own().SIGNI().withClass(CardSIGNIClass.ANGEL).fromTrash()).get();
            attach(getCardIndex(), target, CardUnderType.UNDER_GENERIC);
        }
    }
}
