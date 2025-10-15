package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.GameConst.CardLocation;
import open.batoru.core.gameplay.GameConst.GameEventId;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.ability.Ability;
import open.batoru.data.ability.Ability.AbilityFlag;
import open.batoru.data.ability.AbilityCondition.ConditionState;
import open.batoru.data.ability.AbilityConst.ActionHint;
import open.batoru.data.ability.AbilityConst.UseLimit;
import open.batoru.data.ability.AutoAbility;
import open.batoru.data.ability.events.EventMove;
import open.batoru.data.ability.modifiers.AbilityGainModifier;
import open.batoru.data.ability.stock.StockAbilityShadow;
import open.batoru.game.gfx.GFXCardTextureLayer;
import open.batoru.game.gfx.GFXTextureCardCanvas;

public final class SIGNI_G3_AnneDissonaVerdantBeautyPrincess extends Card {

    public SIGNI_G3_AnneDissonaVerdantBeautyPrincess()
    {
        setImageSets("WXDi-P13-051", "WXDi-P13-051P", "SPDi02-19");

        setOriginalName("翠美姫　アン//ディソナ");
        setAltNames("スイビキアンディソナ Suibiki An Disona");
        setDescription("jp",
                "@E：色１つを指定する。\n" +
                "@C：対戦相手のターンの間、このシグニは[[シャドウ（{{このシグニの@E能力で指定した色$%1}}）]]を得る。\n" +
                "@U $T2：対戦相手の効果１つによって、あなたの手札が１枚以上捨てられるかあなたのエナゾーンからカードが１枚以上トラッシュに置かれたとき、カードを１枚引くか【エナチャージ１】をする。"
        );

        setName("en", "Ann//Dissona, Jade Beauty Queen");
        setDescription("en",
                "@E: Choose a color.\n@C: During your opponent's turn, this SIGNI gains [[Shadow -- {{The color chosen with the @E ability of this SIGNI$%1}}]].\n@U $T2: Whenever one or more cards are discarded from your hand or one or more cards from your Ener Zone are put into your trash by one of your opponent's effects, draw a card or [[Ener Charge 1]]."
        );
        
        setName("en_fan", "Anne//Dissona, Verdant Beauty Princess");
        setDescription("en_fan",
                "@E: Choose 1 color.\n" +
                "@C: During your opponent's turn, this SIGNI gains [[Shadow ({{The color chosen by this SIGNI's @E$%1}})]]\n" +
                "@U $T2: Whenever you discard 1 or more cards from your hand or put 1 or more cards from your ener zone into the trash by one of your opponent's effects, draw 1 card or [[Ener Charge 1]]."
        );

		setName("zh_simplified", "翠美姬 安//失调");
        setDescription("zh_simplified", 
                "@E :颜色1种指定。\n" +
                "@C 对战对手的回合期间，这只精灵得到[[暗影（这只精灵的@E能力指定的颜色）]]。\n" +
                "@U $T2 :当因为对战对手的效果1个，你的手牌1张以上舍弃或从你的能量区把牌1张以上放置到废弃区时，抽1张牌或[[能量填充1]]。\n"
        );

        setCardFlags(CardFlag.DISSONA);

        setType(CardType.SIGNI);
        setColor(CardColor.GREEN);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.BEAUTIFUL_TECHNIQUE);
        setLevel(3);
        setPower(12000);

        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA, PlayFormat.ENGLISH, PlayFormat.CHINESE);
    }

    @Override
    public IndexedInstance newIndexedInstance(int cardId) { return new IndexedInstance(cardId); }
    public class IndexedInstance extends Card.IndexedInstance
    {
        private CardColor chosenColor;
        private int choiceInstanceId;
        public IndexedInstance(int cardId)
        {
            super(cardId);

            registerEnterAbility(this::onEnterEff);

            registerConstantAbility(this::onConstEffCond, new AbilityGainModifier(this::onConstEffModGetSample));

            AutoAbility auto = registerAutoAbility(GameEventId.MOVE, this::onAutoEff);
            auto.setCondition(this::onAutoEffCond);
            auto.setUseLimit(UseLimit.TURN, 2);
            auto.getFlags().addValue(AbilityFlag.ACTIVE_ONCE_PER_EFFECT);
        }
        
        private void onEnterEff()
        {
            chosenColor = playerChoiceColor();
            choiceInstanceId = getInstanceId();
            
            new GFXCardTextureLayer(getCardIndex(), new GFXTextureCardCanvas("colors/"+chosenColor.toString().toLowerCase()+"_icon", 1,2)).attach();
        }
        
        private ConditionState onConstEffCond()
        {
            return !isOwnTurn() && chosenColor != null && choiceInstanceId == getInstanceId() ? ConditionState.OK : ConditionState.BAD;
        }
        private Ability onConstEffModGetSample(CardIndex cardIndex)
        {
            return cardIndex.getIndexedInstance().registerStockAbility(new StockAbilityShadow(this::onAttachedStockEffAddCond, chosenColor::getLabel));
        }
        private ConditionState onAttachedStockEffAddCond(CardIndex cardIndexSource)
        {
            return cardIndexSource.getIndexedInstance().getColor().matches(chosenColor) ? ConditionState.OK : ConditionState.BAD;
        }
        
        private ConditionState onAutoEffCond(CardIndex caller)
        {
            return getEvent().getSourceAbility() != null && !isOwnCard(getEvent().getSourceCardIndex()) &&
                    isOwnCard(caller) && EventMove.getDataMoveLocation() == CardLocation.TRASH && getEvent().isAtOnce(1) &&
                    (caller.isEffectivelyAtLocation(CardLocation.HAND) || caller.isEffectivelyAtLocation(CardLocation.ENER)) ? ConditionState.OK : ConditionState.BAD;
        }
        private void onAutoEff(CardIndex caller)
        {
            if(playerChoiceAction(ActionHint.DRAW, ActionHint.ENER) == 1)
            {
                draw(1);
            } else {
                enerCharge(1);
            }
        }
    }
}

