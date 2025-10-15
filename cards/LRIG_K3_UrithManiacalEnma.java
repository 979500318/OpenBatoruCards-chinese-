package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.ChronoDuration;
import open.batoru.data.DataTable;
import open.batoru.core.gameplay.GameConst.GameEventId;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.CardColor;
import open.batoru.data.CardConst.CardLRIGType;
import open.batoru.data.CardConst.CardType;
import open.batoru.data.CardConst.PlayFormat;
import open.batoru.data.CardDataImageSet.Mask;
import open.batoru.data.Cost;
import open.batoru.data.ability.AbilityConst.UseLimit;
import open.batoru.data.ability.ActionAbility;
import open.batoru.data.ability.AutoAbility;
import open.batoru.data.ability.cost.DiscardCost;
import open.batoru.data.ability.cost.EnerCost;

public final class LRIG_K3_UrithManiacalEnma extends Card {
    
    public LRIG_K3_UrithManiacalEnma()
    {
        setImageSets("WXDi-P06-013", "WXDi-P06-013U", Mask.IGNORE+"WXDi-EXD01-004");
        
        setOriginalName("狂騒の閻魔　ウリス");
        setAltNames("キョウソウノエンマウリス Kyousou no Enma Urisu Ulith");
        setDescription("jp",
                "@E：あなたのトラッシュから黒のシグニを２枚まで対象とし、それらを手札に加える。\n" +
                "@A $T1 %K0：１～３の数字１つを宣言する。あなたのデッキの上からカードを宣言した数字に等しい枚数トラッシュに置く。\n" +
                "@A $G1 @[手札を２枚捨てる]@：ターン終了時まで、このルリグは@>@U $T1：このルリグがアタックしたとき、あなたのトラッシュから##を持たないカード１枚を対象とし、それをライフクロスに加える。@@を得る。"
        );
        
        setName("en", "Urith, Maniacal Enma");
        setDescription("en",
                "@E: Add up to two target black SIGNI from your trash to your hand.\n" +
                "@A $T1 %K0: Declare a number from one to three. Put a number of cards from the top of your deck into your trash equal to the declared number.\n" +
                "@A $G1 @[Discard two cards]@: This LRIG gains@>@U $T1: When this LRIG attacks, add target card without ## from your trash to your Life Cloth.@@until end of turn."
        );
        
        setName("en_fan", "Urith, Maniacal Enma");
        setDescription("en_fan",
                "@E: Target up to 2 black SIGNI from your trash, and add them to your hand.\n" +
                "@A $T1 %K0: Declare a number from 1 to 3. Put the declared number of cards from the top of your deck into the trash.\n" +
                "@A $G1 @[Discard 2 cards from your hand]@: Until end of turn, this LRIG gains:" +
                "@>@U $T1: When this LRIG attacks, target 1 card from your trash without ## @[Life Burst]@, and add it to life cloth."
        );
        
		setName("zh_simplified", "狂骚的阎魔 乌莉丝");
        setDescription("zh_simplified", 
                "@E :从你的废弃区把黑色的精灵2张最多作为对象，将这些加入手牌。\n" +
                "@A $T1 %K01~3的数字1种宣言。从你的牌组上面把宣言数字相等张数的牌放置到废弃区。\n" +
                "@A $G1 手牌2张舍弃:直到回合结束时为止，这只分身得到\n" +
                "@>@U $T1 当这只分身攻击时，从你的废弃区把不持有##的牌1张作为对象，将其加入生命护甲。@@\n"
        );

        setType(CardType.LRIG);
        setLRIGType(CardLRIGType.URITH);
        setColor(CardColor.BLACK);
        setCost(Cost.color(CardColor.BLACK, 2));
        setLevel(3);
        setLimit(6);
        
        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA, PlayFormat.ENGLISH, PlayFormat.CHINESE);
    }
    
    @Override
    public IndexedInstance newIndexedInstance(int cardId) { return new IndexedInstance(cardId); }
    public class IndexedInstance extends Card.IndexedInstance
    {
        public IndexedInstance(int cardId)
        {
            super(cardId);
            
            registerEnterAbility(this::onEnterEff);
            
            ActionAbility act1 = registerActionAbility(new EnerCost(Cost.color(CardColor.BLACK, 0)), this::onActionEff1);
            act1.setUseLimit(UseLimit.TURN, 1);
            
            ActionAbility act2 = registerActionAbility(new DiscardCost(2), this::onActionEff2);
            act2.setUseLimit(UseLimit.GAME, 1);
        }
        
        private void onEnterEff()
        {
            DataTable<CardIndex> data = playerTargetCard(0,2, new TargetFilter(TargetHint.HAND).own().SIGNI().withColor(CardColor.BLACK).fromTrash());
            addToHand(data);
        }
        
        private void onActionEff1()
        {
            int number = playerChoiceNumber(1,2,3);
            millDeck(number);
        }
        
        private void onActionEff2()
        {
            AutoAbility attachedAuto = new AutoAbility(GameEventId.ATTACK, this::onAttachedAutoEff);
            attachedAuto.setUseLimit(UseLimit.TURN, 1);
            
            attachAbility(getCardIndex(), attachedAuto, ChronoDuration.turnEnd());
        }
        private void onAttachedAutoEff()
        {
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.HEAL).own().not(new TargetFilter().lifeBurst()).fromTrash()).get();
            addToLifeCloth(target);
        }
    }
}
