package open.batoru.data.cards;

import open.batoru.core.Deck.DeckPosition;
import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.ChronoDuration;
import open.batoru.core.gameplay.ChronoRecordScheduler.ChronoRecord;
import open.batoru.core.gameplay.GameConst.CardLocation;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.core.gameplay.rulechecks.card.CardRuleCheckRegistry.CardRuleValueType;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.ability.AbilityConst.UseLimit;
import open.batoru.data.ability.ActionAbility;
import open.batoru.data.ability.cost.CoinCost;
import open.batoru.data.ability.cost.DownCost;
import open.batoru.game._3d.Group3D;
import open.batoru.game.animations.AnimationSpinnerRotateCustom;
import open.batoru.game.animations.AnimationSpinnerRotateCustom.SpinDirection;
import open.batoru.game.gfx.GFXZoneSpinner;
import open.batoru.game.gfx.GFXZoneSpinner.GFXSphericalSpinnerObject;

public final class LRIG_K4_GuzukoUselessPrincessOfCheering extends Card {

    public LRIG_K4_GuzukoUselessPrincessOfCheering()
    {
        setImageSets("WXK01-004");

        setOriginalName("応援の駄姫　グズ子");
        setAltNames("オウエンノダキグズコ Ouen no Daki Guzuko");
        setDescription("jp",
                "@E：あなたのデッキの一番上を公開する。それがレベルが奇数のシグニの場合、カードを２枚引く。\n" +
                "@A #D：あなたのトラッシュから＜トリック＞のシグニ１枚を対象とし、それをデッキの一番上に置く。\n" +
                "@A $G1 @[@|ダイレクト|@]@ #C #C：このターン、このルリグは自身のアタックによってダメージを３回与える。"
        );

        setName("en", "Guzuko, Useless Princess of Cheering");
        setDescription("en",
                "@E: Reveal the top card of your deck. If it is a SIGNI with an odd level, draw 2 cards.\n" +
                "@A #D: Target 1 <<Trick>> SIGNI from your trash, and put it on the top of your deck.\n" +
                "@A $G1 @[@|Direct|@]@ #C #C: This turn, this LRIG deals damage 3 times with its attacks."
        );

		setName("zh_simplified", "应援的驮姬 迟钝子");
        setDescription("zh_simplified", 
                "@E :你的牌组最上面公开。其是等级在奇数的精灵的场合，抽2张牌。\n" +
                "@A 横置:从你的废弃区把<<トリック>>精灵1张作为对象，将其放置到牌组最上面。\n" +
                "@A $G1 直击#C #C:这个回合，这只分身因为自己的攻击给予3次伤害。（对战对手能[[防御]]3次最多）\n"
        );

        setLRIGType(CardLRIGType.GUZUKO);
        setType(CardType.LRIG);
        setColor(CardColor.BLACK);
        setLevel(4);
        setLimit(11);

        setPlayFormat(PlayFormat.KEY);
    }

    @Override
    public IndexedInstance newIndexedInstance(int cardId) { return new IndexedInstance(cardId); }
    public class IndexedInstance extends Card.IndexedInstance
    {
        public IndexedInstance(int cardId)
        {
            super(cardId);
            
            registerEnterAbility(this::onEnterEff);
            
            registerActionAbility(new DownCost(), this::onActionEff1);

            ActionAbility act2 = registerActionAbility(new CoinCost(2), this::onActionEff2);
            act2.setUseLimit(UseLimit.GAME, 1);
            act2.setName("Direct");
        }
        
        private void onEnterEff()
        {
            CardIndex cardIndex = reveal();
            
            if(cardIndex == null || !CardType.isSIGNI(cardIndex.getIndexedInstance().getTypeByRef()) || cardIndex.getIndexedInstance().getLevelByRef() % 2 == 0 ||
               draw(2).get() == null)
            {
                returnToDeck(cardIndex, DeckPosition.TOP);
            }
        }
        
        private void onActionEff1()
        {
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.TOP).own().SIGNI().withClass(CardSIGNIClass.TRICK).fromTrash()).get();
            returnToDeck(target, DeckPosition.TOP);
        }
        
        private void onActionEff2()
        {
            ChronoRecord record = new ChronoRecord(ChronoDuration.turnEnd());

            Group3D[] spinNodes = new Group3D[3];
            for(int i=0;i<spinNodes.length;i++) spinNodes[i] = new GFXSphericalSpinnerObject(40,80, 25, new int[]{200, 80, 100});
            GFXZoneSpinner spinner = new GFXZoneSpinner(getOwner(),CardLocation.LRIG, new AnimationSpinnerRotateCustom(5000, 130, -50, SpinDirection.CLOCKWISE), spinNodes);
            GFXZoneSpinner.attachToChronoRecord(record, spinner);

            setBaseValue(getCardIndex(), getRCRegistry().getRuleValue(CardRuleValueType.ATTACK_DAMAGE_INSTANCES), 3, record);
        }
    }
}
